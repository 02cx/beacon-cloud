package com.dong.cache.controller;

import com.msb.framework.redis.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 类描述：飞马框架测试  redis
 *
 * @author wuyadong
 * @date 2025/4/12 下午6:46
 */
@RestController
public class TestController {

    @Autowired
    private RedisClient redisClient;

    /**
     * 测试存储对象数据
     * @param key
     * @param map
     * @return
     */
    @PostMapping("/test/set/{key}")
    public String set(@PathVariable String key, @RequestBody Map map){
        redisClient.hSet(key, map);
        return "success";
    }

    /**
     * 测试获取对象数据
     * @param key
     * @return
     */
    @GetMapping("/test/get/{key}")
    public Map get(@PathVariable String key){
        return redisClient.hGetAll(key);
    }
}
