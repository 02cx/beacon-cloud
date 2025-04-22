package com.dong.strategy.filter.impl;

import com.dong.common.constant.CacheKeyConstant;
import com.dong.common.constant.RabbitMQConstant;
import com.dong.common.domain.MobileAreaDTO;
import com.dong.common.domain.MobileResponse;
import com.dong.common.enums.ExceptionEnums;
import com.dong.common.exception.StrategyException;
import com.dong.common.model.StandardSubmit;
import com.dong.common.util.MobileOpratorUtil;
import com.dong.common.util.OperatorUtil;
import com.dong.strategy.feign.BeaconCacheFeign;
import com.dong.strategy.filter.StrategyFilter;
import com.google.common.base.Strings;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 号段补全：获取手机号的运营商以及对应的归属地
 *
 * @author wuyadong
 * @date 2025/4/19 下午6:01
 */
@Component("phase")
@Slf4j
public class PhaseStrategyFilter implements StrategyFilter {

    /**
     * 切割手机前7位
     */
    private final int MOBILE_START = 0;
    private final int MOBILE_end = 7;

    @Autowired
    private BeaconCacheFeign beaconCacheFeign;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Override
    public void check(StandardSubmit standardSubmit)  {
        // 根据手机号的前7位，查询对应手机信息
        String mobile = standardSubmit.getMobile();
        String mobileInfo = mobile.substring(MOBILE_START, MOBILE_end);

        if(Strings.isNullOrEmpty(mobile)){
            throw new StrategyException(ExceptionEnums.MOBILE_EMPTY);
        }
        MobileAreaDTO phase = beaconCacheFeign.getPhase(CacheKeyConstant.PHASE + mobile);
        if(phase == null){
            // 缓存中不存在对应的手机信息，调用第三方接口查询
            MobileResponse mobileResponse = MobileOpratorUtil.getMobileInfo(mobileInfo);
            if(mobileResponse == null){
                throw new StrategyException(ExceptionEnums.ERROR_MOBILE);
            }
            // 发送消息到MQ，并且同步到MySQL和Redis
            rabbitTemplate.convertAndSend(RabbitMQConstant.MOBILE_AREA_OPERATOR,mobileResponse);
            standardSubmit.setArea(mobileResponse.getData().getProvince() + mobileResponse.getData().getCity());
            standardSubmit.setOperatorId(OperatorUtil.getOperatorId(mobileResponse.getData().getSp()));
            log.info("【短信模块】手机号补全成功，手机号 = {}，手机运营商 = {}，手机归属地 = {}",standardSubmit.getMobile(),mobileResponse.getData().getSp(),mobileResponse.getData().getProvince() + mobileResponse.getData().getCity());
            return;
        }

        //无论是Redis还是三方接口查询到之后，封装到StandardSubmit对象中
        standardSubmit.setArea(phase.getMobileArea());
        standardSubmit.setOperatorId(OperatorUtil.getOperatorId(phase.getMobileType()));
        log.info("【短信模块】手机号补全成功，手机号 = {}，手机运营商 = {}，手机归属地 = {}",standardSubmit.getMobile(),phase.getMobileType(),phase.getMobileArea());
    }
}
