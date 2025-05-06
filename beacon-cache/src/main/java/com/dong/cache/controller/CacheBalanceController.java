package com.dong.cache.controller;

import com.dong.common.domain.MobileAreaDTO;
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

    @PostMapping("/cache/pipeline/string")
    public void pipelineString(@RequestBody Map<String,String> map){
        log.info("【缓存模块】 pipelineString，获取到存储的数据，map的长度 ={}的数据", map.size());
        redisClient.pipelined(operations -> {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                operations.opsForValue().set(entry.getKey(),entry.getValue());
            }
        });
    }

    @GetMapping("/cache/phase/get/{key}")
    public MobileAreaDTO getPhase(@PathVariable String key) {
        MobileAreaDTO mobileAreaDTO = redisClient.get(key);
        log.info("【缓存模块】获取到手机号码{}的归属地信息：{}", key, mobileAreaDTO);
        return mobileAreaDTO;
    }


    @PostMapping("/cache/hIncrBy/{key}/{field}/{delta}")
    public Long hIncrBy(@PathVariable String key,@PathVariable String  field, @PathVariable Long delta){
        log.info("【缓存模块】 hIncrBy方法，存储key = {}，存储field = {}，存储delta = {}",key,field,delta);
        Long res = redisClient.hIncrementBy(key, field, delta);
        log.info("【缓存模块】 hIncrBy方法，存储key = {}，存储field = {}，剩余数值 = {}",key,field,res);
        return res;
    }
}
