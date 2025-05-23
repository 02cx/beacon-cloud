package com.dong.cache.controller;

import com.msb.framework.redis.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

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

    /**
     * 获取isCallback  是否回调
     * @param key
     * @param field
     * @return
     */
    @GetMapping("/cache/hget/isCallback/{key}/{field}")
    public Integer hgetIsCallback(@PathVariable(value = "key")String key, @PathVariable(value = "field")String field){
        Integer value = redisClient.hGet(key, field);
        return value;
    }


    /**
     * 获取callbackUrl 回调地址
     * @param key
     * @param field
     * @return
     */
    @GetMapping("/cache/hget/callbackUrl/{key}/{field}")
    public String hgetCallbackUrl(@PathVariable(value = "key")String key, @PathVariable(value = "field")String field){
        String value = redisClient.hGet(key, field);
        return value;
    }


    @PostMapping("/cache/ratelimiter/zadd/{key}/{score}/{member}")
    public Boolean zadd(@PathVariable(value = "key")String key, @PathVariable(value = "score")Long score, @PathVariable(value = "member")String member){
        log.info("【缓存模块】 zadd方法，存储key = {}，存储value = {}",key,member);
        boolean res = redisClient.zAdd(key, member, score);
        return res;
    }

    @GetMapping("/cache/ratelimiter/zrange/{key}/{start}/{end}")
    public Integer zrangeByScore(@PathVariable(value =  "key") String key,@PathVariable(value = "start") Double start, @PathVariable(value = "end") Double end){
        Set<Object> values = redisClient.zRangeByScore(key, start, end);
        if(values != null){
            return values.size();
        }
        return 0;
    }

    @PostMapping("/cache/ratelimiter/zremove/{key}/{member}")
    public void zRemove(@PathVariable(value = "key")String key, @PathVariable(value = "member")String member){
        redisClient.zRemove(key, member);
    }

}
