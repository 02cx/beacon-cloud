package com.dong.api.filter.impl;

import com.dong.api.client.BeaconCacheClient;
import com.dong.api.filter.CheckFilter;
import com.dong.common.constant.ApiConstant;
import com.dong.common.constant.CacheKeyConstant;
import com.dong.common.enums.ExceptionEnums;
import com.dong.common.exception.ApiException;
import com.dong.common.model.StandardSubmit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 类描述：客户剩余的金额是否充足的检验具体类
 *
 * @author wuyadong
 * @date 2025/4/5 下午10:51
 */
@Component("fee")
@Slf4j
public class FeeCheckFilter implements CheckFilter {

    /**
     * 只要短信内容的文字长度小于等于70个字，按照一条计算
     */
    private final int MAX_LENGTH = 70;

    /**
     * 如果短信内容的文字长度超过70，67字/条计算
     */
    private final int LOOP_LENGTH = 67;

    @Autowired
    private BeaconCacheClient beaconCacheClient;

    @Override
    public void check(StandardSubmit standardSubmit) {
        // 获取短信内容长度
        int length = standardSubmit.getText().length();
        // 判断短信内容长度，采取对应的处理逻辑
        if(length <= MAX_LENGTH){
            standardSubmit.setFee(ApiConstant.SIGNAL_FEE);
        }else{
           int strip = (int)Math.ceil(length/LOOP_LENGTH);
           standardSubmit.setFee(ApiConstant.SIGNAL_FEE * strip);
        }
        //从缓存中查询出客户的余额
        Long balance = beaconCacheClient.hget(CacheKeyConstant.CLIENT_BALANCE + standardSubmit.getClientId(), "balance");
        if(balance < standardSubmit.getFee()){
            log.info("【短信模块】客户剩余的金额不足，客户id = {}，剩余金额 = {}，短信费 = {}",standardSubmit.getClientId(),balance,standardSubmit.getFee());
            throw new ApiException(ExceptionEnums.BALANCE_NOT_ENOUGH);
        }
        log.info("【短信模块】客户剩余的金额充足，客户id = {}，剩余金额 = {}，短信费 = {}",standardSubmit.getClientId(),balance,standardSubmit.getFee());

    }


}
