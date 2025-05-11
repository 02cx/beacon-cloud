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
 * 类描述：一个小时内发送三次
 *
 * @author wuyadong
 * @date 2025/5/3 下午9:23
 */
@Component("routeLimiteOneHour")
@Slf4j
public class RouteLimiteOneHourStrategyFilter implements StrategyFilter {

    @Autowired
    private BeaconCacheFeign beaconCacheFeign;

    @Autowired
    private ErrorSendMsgUtil errorSendMsgUtil;

    private final long ONE_HOUR = 60 * 60 * 1000 - 1;

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
        String key = CacheKeyConstant.ROUTE_LIMITER_ONE_HOURS + clientId + ":" + mobile;
        // 失败重试
        int retry = 0;
        while(!beaconCacheFeign.zadd(key, time, time + "")){
            if(retry++ == 2) {
                log.info("【策略模块-一小时内发生三次限流器】   当前手机号发送短信请求被限流！失败重试 mobile = {}",mobile);
                standardSubmit.setReportErrorMsg(ExceptionEnums.ONE_HOURS_LIMIT.getMsg() + ",mobile = " + mobile);
                // 推送状态报告以及日志
                errorSendMsgUtil.sendPushReport(standardSubmit);
                errorSendMsgUtil.sendWriteLog(standardSubmit);
                throw new StrategyException(ExceptionEnums.ONE_HOURS_LIMIT);
            }
            // 重置时间
            time = System.currentTimeMillis();
        }


        // 成功添加了数据，并且没有达到重试次数
        // 基于zrangebyscore查询1个小时内发生了多少条短信
        Integer count = beaconCacheFeign.zrangeByScore(CacheKeyConstant.ROUTE_LIMITER_ONE_HOURS + clientId + ":" + mobile, (double) (time - ONE_HOUR), (double) time);

        // 如果1小时内大于3条，则需要进行限流
        if(count > 3){
            log.info("【策略模块-一小时内发生三次限流器】   当前手机号发送短信请求被限流！失败重试 mobile = {}",mobile);
            // 推送状态报告之前移除本次缓存的数据
            beaconCacheFeign.zRemove(key,String.valueOf(time));
            // 设置状态报告
            standardSubmit.setReportErrorMsg(ExceptionEnums.ONE_HOURS_LIMIT.getMsg() + ",mobile = " + mobile);
            // 推送状态报告以及日志
            errorSendMsgUtil.sendPushReport(standardSubmit);
            errorSendMsgUtil.sendWriteLog(standardSubmit);
            throw new StrategyException(ExceptionEnums.ONE_HOURS_LIMIT);
        }

        log.info("【策略模块-一小时内发生三次限流器】   当前手机号发送短信请求通过限流器！ mobile = {}",mobile);

    }
}
