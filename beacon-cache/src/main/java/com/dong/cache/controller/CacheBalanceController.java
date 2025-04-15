package com.dong.cache.controller;

import com.msb.framework.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public void setBalance(@PathVariable String key, @RequestParam("value")String value){
        redisClient.set(key,value);
    }
}
