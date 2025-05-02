package com.dong.strategy.filter.impl;

import com.dong.common.constant.CacheKeyConstant;
import com.dong.common.constant.RabbitMQConstant;
import com.dong.common.enums.ExceptionEnums;
import com.dong.common.exception.StrategyException;
import com.dong.common.model.StandardReport;
import com.dong.common.model.StandardSubmit;
import com.dong.strategy.feign.BeaconCacheFeign;
import com.dong.strategy.filter.StrategyFilter;
import com.dong.strategy.util.ErrorSendMsgUtil;
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
    private ErrorSendMsgUtil errorSendMsgUtil;


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
            // 发送消息到写日志队列
            standardSubmit.setReportErrorMsg(ExceptionEnums.DIRTWORD_EXIST.getMsg() + "dirtyWords = " + dirtyWords.toString());
            errorSendMsgUtil.sendWriteLog(standardSubmit);

            // 发送状态报告的消息前，需要将report对象数据封装
            errorSendMsgUtil.sendPushReport(standardSubmit);

            // 抛出异常
            throw new StrategyException(ExceptionEnums.DIRTWORD_EXIST);
        }
    }
}
