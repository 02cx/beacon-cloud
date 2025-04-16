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
 * @date 2025/4/15 下午6:02
 */
@RestController
@Slf4j
public class CacheBusinessController {

    @Autowired
    private RedisClient redisClient;


    @PostMapping("/cache/hmset/{key}")
    public void hmset(@PathVariable(value = "key")String key,@RequestBody Map<String,Object> map) {
        log.info("【缓存模块】 hmset方法，存储key = {}，存储value = {}",key,map);
        redisClient.hSet(key,map);
    }

    @PostMapping(value = "/cache/set/{key}")
    public void set(@PathVariable(value = "key")String key, @RequestParam(value = "value")String value){
        log.info("【缓存模块】 set方法，存储key = {}，存储value = {}",key,value);
        redisClient.set(key,value);
    }


    /**
     * 获取hashmap
     * @param key
     * @return
     */
    @GetMapping("/cache/hgetall/{key}")
    public Map<String, Object> hgetall(@PathVariable(value = "key")String key){
        log.info("【缓存模块】 hgetall方法，存储key = {}",key);
        Map<String, Object> stringObjectMap = redisClient.hGetAll(key);
        log.info("【缓存模块】 hgetall方法，存储key = {}，存储value = {}",key,stringObjectMap);
        return stringObjectMap;
    }
}
