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

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 类描述：一分钟内只能发送一次的限流器
 *
 * @author wuyadong
 * @date 2025/5/3 下午9:23
 */
@Component("routeLimiteOneMinus")
@Slf4j
public class RouteLimiteOneMinusStrategyFilter implements StrategyFilter {

    @Autowired
    private BeaconCacheFeign beaconCacheFeign;

    @Autowired
    private ErrorSendMsgUtil errorSendMsgUtil;

    private final long ONE_MINUTE = 60 * 1000 - 1;

    @Override
    public void check(StandardSubmit standardSubmit) {
        // 判断短信类型不是验证码类的，直接结束方法
        if(standardSubmit.getState() != 0){
            return;
        }

        // 获取短信发送时间
        LocalDateTime sendTime = standardSubmit.getSendTime();
        // 转为毫秒数
        long time = sendTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();

        // 获取客户标识以及手机号信息
        Long clientId = standardSubmit.getClientId();
        String mobile = standardSubmit.getMobile();

        // 将当前的短信发送信息存储到redis的Zset结构中
        String key = CacheKeyConstant.ROUTE_LIMITER_ONE_MINUS + clientId + ":" + mobile;
        Boolean res = beaconCacheFeign.zadd(key, time, time + "");
        // 如果添加失败，表示同一时刻还有其他的短信发送请求，因为1分钟内只能发送一次，则直接返回限流
        if(!res){
            log.info("【策略模块-一分钟内只能发送一次限流器】   当前手机号发送短信请求被限流！同一时刻发生并发导致 mobile = {}",mobile);
            standardSubmit.setReportErrorMsg(ExceptionEnums.ONE_MINUS_LIMIT + ",mobile = " + mobile);
            // 推送状态报告以及日志
            errorSendMsgUtil.sendPushReport(standardSubmit);
            errorSendMsgUtil.sendWriteLog(standardSubmit);
            throw new StrategyException(ExceptionEnums.ONE_MINUS_LIMIT);
        }

        // 基于zrangebyscore查询1分钟内有几条短信
        Integer count = beaconCacheFeign.zrangeByScore(CacheKeyConstant.ROUTE_LIMITER_ONE_MINUS + clientId + ":" + mobile, (double) (time - ONE_MINUTE), (double) time);

        // 如果1分钟内大于1条，则需要进行限流
        if(count > 1){
            log.info("【策略模块-一分钟内只能发送一次限流器】   当前手机号发送短信请求被限流！1分钟内发送短信条数大于1条 mobile = {}",mobile);
            // 推送状态报告之前移除本次缓存的数据
            beaconCacheFeign.zRemove(key,String.valueOf(time));
            // 设置状态报告
            standardSubmit.setReportErrorMsg(ExceptionEnums.ONE_MINUS_LIMIT + ",mobile = " + mobile);
            errorSendMsgUtil.sendPushReport(standardSubmit);
            errorSendMsgUtil.sendWriteLog(standardSubmit);
            throw new StrategyException(ExceptionEnums.ONE_MINUS_LIMIT);
        }

        log.info("【策略模块-一分钟内只能发送一次限流器】   当前手机号发送短信请求通过限流器！ mobile = {}",mobile);

    }
}
