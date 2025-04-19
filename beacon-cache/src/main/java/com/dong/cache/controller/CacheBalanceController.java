package com.dong.cache.controller;

import com.msb.framework.redis.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/15 下午9:17
 */
@RestController
@Slf4j
public class CacheBalanceController {

    @Autowired
    private RedisClient redisClient;


    @PostMapping("/cache/balance/set/{key}")
    public void setBalance(@PathVariable String key, @RequestBody Map<String, Object> clientBalance) {
        redisClient.hSet(key, clientBalance);
    }

    @GetMapping("/cache/balancce/hget/{key}")
    public Long hget(@PathVariable String key, @RequestParam String field) {
        log.info(String.valueOf(redisClient.<Long>hGet(key, field)));
        Integer balacne =  redisClient.hGet(key, field);
        log.info("【缓存模块】客户{}的余额：{}", key, balacne);
        return balacne.longValue();
    }

    @GetMapping("/cache/balance/hgetfilters/{key}/{field}")
    public String hgetFilters(@PathVariable String key, @PathVariable String field) {
        String filter =  redisClient.hGet(key, field);
        log.info("【缓存模块】客户{}的动态配置的过滤顺序：{}", key, filter);
        return String.valueOf(filter);
    }
}
