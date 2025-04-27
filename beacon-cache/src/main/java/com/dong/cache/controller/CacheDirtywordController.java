package com.dong.cache.controller;

import com.msb.framework.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/27 上午11:50
 */
@RestController
public class CacheDirtywordController {

    @Autowired
    private RedisClient redisClient;


    @PostMapping("/cache/dirtyword/sadd/{key}")
    public void sadd(@PathVariable String key,@RequestBody String... values){
        redisClient.sAdd(key,values);
    }
}
