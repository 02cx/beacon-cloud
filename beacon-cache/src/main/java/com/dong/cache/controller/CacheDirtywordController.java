package com.dong.cache.controller;

import com.msb.framework.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

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

    @GetMapping("/cache/dirtyword/smembers/{key}")
    public Set<String> smembers(@PathVariable String key){
        return redisClient.sMembers(key);
    }

    @GetMapping("/cache/route/smembers/{key}")
    public Set<Map> smembersRoute(@PathVariable String key){
        return redisClient.sMembers(key);
    }
}
