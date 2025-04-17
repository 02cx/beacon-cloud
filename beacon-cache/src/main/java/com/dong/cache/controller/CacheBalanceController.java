package com.dong.cache.controller;

import com.dong.cache.domain.ClientBalance;
import com.msb.framework.redis.RedisClient;
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
public class CacheBalanceController {

    @Autowired
    private RedisClient redisClient;


    @PostMapping("/cache/balance/set/{key}")
    public void setBalance(@PathVariable String key, @RequestBody Map<String,Object> clientBalance){
        redisClient.hSet(key,clientBalance);
    }
}
