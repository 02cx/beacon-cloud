package com.dong.strategy.filter.impl;

import com.dong.common.constant.CacheKeyConstant;
import com.dong.common.enums.ExceptionEnums;
import com.dong.common.exception.StrategyException;
import com.dong.common.model.StandardSubmit;
import com.dong.strategy.feign.BeaconCacheFeign;
import com.dong.strategy.filter.StrategyFilter;
import com.dong.strategy.util.ErrorSendMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 类描述：黑名单过滤策略
 *
 * @author wuyadong
 * @date 2025/4/19 下午6:12
 */
@Component("blackClient")
@Slf4j
public class BlackListClientStrategyFilter implements StrategyFilter {

    @Autowired
    private ErrorSendMsgUtil sendMsgUtil;

    @Autowired
    private BeaconCacheFeign cacheClient;

    // 黑名单的默认value
    private final String TRUE = "1";

    @Override
    public void check(StandardSubmit submit) {
        log.info("【策略模块-客户级别黑名单校验】   校验ing…………");
        //1、获取发送短信的手机号,以及客户的ID
        String mobile = submit.getMobile();
        Long clientId = submit.getClientId();

        //2、调用Redis查询
        String value = cacheClient.getBlack(CacheKeyConstant.BLACK + clientId + ":" + mobile);

        //3、如果查询的结果为"1"，代表是黑名单
        if(TRUE.equals(value)){
            log.info("【策略模块-客户级别黑名单校验】   当前发送的手机号是客户黑名单！ mobile = {}",mobile);
            submit.setReportErrorMsg(ExceptionEnums.BLACK_CLIENT + ",mobile = " + mobile);
            sendMsgUtil.sendWriteLog(submit);
            sendMsgUtil.sendPushReport(submit);
            throw new StrategyException(ExceptionEnums.BLACK_CLIENT);
        }
        //4、不是1，正常结束
        log.info("【策略模块-客户级别黑名单校验】   当前手机号不是客户黑名单！ ");
    }
}
