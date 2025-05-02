package com.dong.strategy.util;

import com.dong.common.constant.CacheKeyConstant;
import com.dong.common.constant.RabbitMQConstant;
import com.dong.common.enums.ExceptionEnums;
import com.dong.common.model.StandardReport;
import com.dong.common.model.StandardSubmit;
import com.dong.strategy.feign.BeaconCacheFeign;
import org.apache.logging.log4j.util.Strings;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/5/2 下午7:53
 */

@Component
public class ErrorSendMsgUtil {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BeaconCacheFeign cacheClient;

    /**
     * 策略模块校验未通过，发送写日志操作
     * @param submit
     */
    public void sendWriteLog(StandardSubmit submit) {
        submit.setReportErrorMsg(submit.getReportErrorMsg());
        submit.setReportState(2);
        // 发送消息到写日志队列
        rabbitTemplate.convertAndSend(RabbitMQConstant.SMS_WRITE_LOG,submit);
    }

    /**
     * 策略模块校验未通过，发送状态报告操作
     */
    public void sendPushReport(StandardSubmit submit) {
        // 查询当前客户的isCallback
        Integer isCallback = cacheClient.hgetIsCallback(CacheKeyConstant.CLIENT_BUSINESS + submit.getApiKey(), "isCallback");
        // 查看是否需要给客户一个回调
        if(isCallback == 1){
            // 如果需要回调，再查询客户的回调地址
            String callbackUrl = cacheClient.hgetCallbackUrl(CacheKeyConstant.CLIENT_BUSINESS + submit.getApiKey(), "callbackUrl");
            // 如果回调地址不为空。
            if(!Strings.isEmpty(callbackUrl)){
                // 封装客户的报告推送的信息，开始封装StandardReport
                StandardReport report = new StandardReport();
                BeanUtils.copyProperties(submit,report);
                report.setIsCallback(isCallback);
                report.setCallbackUrl(callbackUrl);
                // 发送消息到RabbitMQ
                rabbitTemplate.convertAndSend(RabbitMQConstant.SMS_PUSH_REPORT,report);
            }

        }
    }


}
