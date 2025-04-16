package com.dong.cache.controller;

import com.msb.framework.redis.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/15 下午6:22
 */
@RestController
@Slf4j
public class CacheSignController {

    @Autowired
    private RedisClient redisClient;

    @PostMapping(value = "/cache/sadd/{key}")
    public void sadd(@PathVariable(value = "key")String key, @RequestBody Map<String,Object>... value){
        redisClient.sAdd(key,value);
    }

    /**
     *  根绝key查询出所有的sign
     * @param key
     * @return
     */
    @GetMapping("/cache/sign/smembers/{key}")
    public Set<Map> smembers(@PathVariable(value = "key")String key){
        log.info("【缓存模块 sign】 zall方法，存储key = {}",key);
        Set<Map> smembers = redisClient.sMembers(key);
        log.info("【缓存模块 sign】 zall方法，存储key = {}，存储value = {}",key,smembers);
        return smembers;
    }
}
