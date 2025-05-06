package com.dong.strategy.filter.impl;

import com.dong.common.constant.CacheKeyConstant;
import com.dong.common.constant.RabbitMQConstant;
import com.dong.common.enums.ExceptionEnums;
import com.dong.common.exception.StrategyException;
import com.dong.common.model.StandardSubmit;
import com.dong.strategy.feign.BeaconCacheFeign;
import com.dong.strategy.filter.StrategyFilter;
import com.dong.strategy.util.ChannelTransferUtil;
import com.dong.strategy.util.ErrorSendMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 类描述：路由过滤策略
 *
 * @author wuyadong
 * @date 2025/4/19 下午6:12
 */
@Component("route")
@Slf4j
public class RouteStrategyFilter implements StrategyFilter {

    @Autowired
    private BeaconCacheFeign beaconCacheFeign;

    @Autowired
    private ErrorSendMsgUtil errorSendMsgUtil;

    @Autowired
    private AmqpAdmin amqpAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void check(StandardSubmit standardSubmit) {
        log.info("路由过滤策略开始执行");
        // 拿到客户id
        Long clientId = standardSubmit.getClientId();
        // 基于redis获取客户绑定的所有通道信息
        Set<Map> smembers = beaconCacheFeign.smembersRoute(CacheKeyConstant.CLIENT_CHANNEL + ":" + clientId);

        // 将通道信息按照权重进行排序
        TreeSet<Map> treeSet =  new TreeSet<>(new Comparator<Map>() {
            @Override
            public int compare(Map o1, Map o2) {
                Integer o1Weight = Integer.parseInt(o1.get("clientChannelWeight") + "");
                Integer o2Weight = Integer.parseInt(o2.get("clientChannelWeight") + "");
                return o2Weight - o1Weight;
            }
        });
        treeSet.addAll(smembers);

        boolean ok = false;
        Map channel = null;
        Map clientChannel = null;
        //4、基于排好序的通道选择，权重更高的
        for (Map clientWeightChannel : treeSet) {
            //5、如果客户和通道的绑定关系可用，直接去基于Redis查询具体的通道信息
            if((int)(clientWeightChannel.get("isAvailable")) != 0){
                // 当前关系不可用，直接进行下次循环，选择权重相对更低一点的
                continue;
            }

            //6、如果通道信息查询后，判断通道是否可用，其次运营商可以匹配。
            channel = beaconCacheFeign.getChannel("channel:" + clientWeightChannel.get("channelId"));
            if((int)(channel.get("isAvailable")) != 0){
                // 当前通道不可用，选择权重更低的通道~
                continue;
            }
            // 获取通道的通讯方式
            Integer channelType = (Integer) channel.get("channelType");
            if (channelType != 0 && standardSubmit.getOperatorId() != channelType){
                // 通道不是全网通，并且和当前手机号运营商不匹配
                continue;
            }

            //7、如果后期涉及到的通道的转换，这里留一个口子
            Map transferChannel = ChannelTransferUtil.transfer(standardSubmit, channel);

            // 找到可以使用的通道了
            ok = true;
            clientChannel = clientWeightChannel;
            break;
        }

        if(!ok){
            log.info("【策略模块-路由策略】   没有选择到可用的通道！！");
            standardSubmit.setReportErrorMsg(ExceptionEnums.NO_CHANNEL.getMsg());
            errorSendMsgUtil.sendWriteLog(standardSubmit);
            errorSendMsgUtil.sendPushReport(standardSubmit);
            throw new StrategyException(ExceptionEnums.NO_CHANNEL);
        }
        //8、基于选择的通道封装submit的信息
        standardSubmit.setChannelId(Long.parseLong(channel.get("id") + ""));
        standardSubmit.setSrcNumber("" + channel.get("channelNumber") + clientChannel.get("clientChannelNumber"));

        try {
            //9、声明好队列名称，并构建队列
            String queueName = RabbitMQConstant.SMS_GATEWAY + standardSubmit.getChannelId();
            amqpAdmin.declareQueue(QueueBuilder.durable(queueName).build());

            //10、发送消息到声明好的队列中
            rabbitTemplate.convertAndSend(queueName,standardSubmit);
        } catch (AmqpException e) {
            log.info("【策略模块-路由策略】   声明通道对应队列以及发送消息时出现了问题！");
            standardSubmit.setReportErrorMsg(e.getMessage());
            errorSendMsgUtil.sendWriteLog(standardSubmit);
            errorSendMsgUtil.sendPushReport(standardSubmit);
            throw new StrategyException(e.getMessage(),ExceptionEnums.UNKNOWN_ERROR.getCode());
        }
    }
}
