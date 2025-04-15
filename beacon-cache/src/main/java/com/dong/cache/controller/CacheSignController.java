package com.dong.cache.controller;

import com.msb.framework.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/15 下午6:22
 */
@RestController
public class CacheSignController {

    @Autowired
    private RedisClient redisClient;

    @PostMapping(value = "/cache/sadd/{key}")
    public void sadd(@PathVariable(value = "key")String key, @RequestBody Map<String,Object>... value){
        redisClient.sAdd(key,value);
    }
}
