package com.dong.strategy.filter.impl;

import com.dong.common.constant.CacheKeyConstant;
import com.dong.common.constant.RabbitMQConstant;
import com.dong.common.enums.ExceptionEnums;
import com.dong.common.exception.StrategyException;
import com.dong.common.model.StandardReport;
import com.dong.common.model.StandardSubmit;
import com.dong.strategy.feign.BeaconCacheFeign;
import com.dong.strategy.filter.StrategyFilter;
import com.dong.strategy.util.HutoolDFAUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类描述：敏感词过滤器
 *
 * @author wuyadong
 * @date 2025/4/19 下午6:10
 */
@Component("dirtyword")
@Slf4j
public class CaseSensitiveStrategyFilter implements StrategyFilter {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Autowired
    private BeaconCacheFeign beaconCacheFeign;

    @Override
    public void check(StandardSubmit standardSubmit) {
        log.info("敏感词过滤器");
        // 获取短信内容
        String text = standardSubmit.getText();

        // 调用DFA查看敏感词
        List<String> dirtyWords = HutoolDFAUtil.getDirtyWords(text);
        log.info("敏感词比对后的列表：{}", dirtyWords);

        if (dirtyWords != null && dirtyWords.size() > 0) {
            // 存在敏感词 抛出异常/其他处理
            // 封装错误信息
            standardSubmit.setReportErrorMsg(ExceptionEnums.DIRTWORD_EXIST.getMsg() + "dirtywords:" + dirtyWords);
            standardSubmit.setReportState(2);
            // 发送消息到写日志队列
            rabbitTemplate.convertAndSend(RabbitMQConstant.SMS_WRITE_LOG, standardSubmit);

            // 发送状态报告的消息前，需要将report对象数据封装
            Integer isCallback = beaconCacheFeign.hgetIsCallback(CacheKeyConstant.CLIENT_BUSINESS + standardSubmit.getApiKey(), "isCallback");
            // 客户需要回调
            if(isCallback == 1){
                String callbackUrl = beaconCacheFeign.hgetCallbackUrl(CacheKeyConstant.CLIENT_BUSINESS + standardSubmit.getApiKey(), "callbackUrl");
                // 回调地址不为空
                if(!Strings.isEmpty(callbackUrl)){
                    // 封装客户的报告推送的信息
                    StandardReport standardReport = new StandardReport();
                    BeanUtils.copyProperties(standardSubmit, standardReport);
                    standardReport.setCallbackUrl(callbackUrl);
                    standardReport.setIsCallback(isCallback);
                    // 发送消息到RabbitMQ
                    rabbitTemplate.convertAndSend(RabbitMQConstant.SMS_PUSH_REPORT, standardReport);
                }
            }

            // 抛出异常
            throw new StrategyException(ExceptionEnums.DIRTWORD_EXIST);
        }
    }
}
