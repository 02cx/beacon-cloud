package com.dong.strategy.filter.impl;

import com.dong.common.enums.ExceptionEnums;
import com.dong.common.exception.StrategyException;
import com.dong.common.model.StandardSubmit;
import com.dong.strategy.feign.BeaconCacheFeign;
import com.dong.strategy.filter.StrategyFilter;
import com.dong.strategy.util.ClientBalanceUtil;
import com.dong.strategy.util.ErrorSendMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 类描述：扣费策略
 *
 * @author wuyadong
 * @date 2025/5/6 上午9:21
 */
@Slf4j
@Component("fee")
public class FeeStrategyFilter implements StrategyFilter {

    @Autowired
    private BeaconCacheFeign beaconCacheFeign;

    @Autowired
    private ErrorSendMsgUtil errorSendMsgUtil;

    private final String FEE_KEY = "fee:";

    @Override
    public void check(StandardSubmit standardSubmit) {
        log.info("【策略模块-扣费策略】   ing…………");
        // 获取封装的金额
        Long fee = standardSubmit.getFee();
        Long clientId = standardSubmit.getClientId();
        String key = FEE_KEY + clientId;
        // 调用接口进行扣减
        Long amount = beaconCacheFeign.hIncrBy(key, "balance", -fee);
        // 获取当前客户欠费的金额的最大限制
        Long amountLimit = ClientBalanceUtil.getClientAmountLimit(clientId);

        // 判断扣费后，用户的余额是否超过了最大限制
        if(amount < amountLimit){
            log.info("【策略模块-扣费策略】   扣费失败，用户余额不足，当前用户余额为：{}", amount);
            // 需要将扣除的用户余额恢复
            beaconCacheFeign.hIncrBy(key, "balance", fee);
            standardSubmit.setReportErrorMsg(ExceptionEnums.BALANCE_NOT_ENOUGH.getMsg());
            errorSendMsgUtil.sendWriteLog(standardSubmit);
            errorSendMsgUtil.sendPushReport(standardSubmit);
            throw new StrategyException(ExceptionEnums.BALANCE_NOT_ENOUGH);
        }
        log.info("【策略模块-扣费策略】   扣费成功，当前用户余额为：{}", amount);
    }
}
