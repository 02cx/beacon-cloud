package com.dong.cache.controller;

import com.msb.framework.redis.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/5/2 下午10:48
 */
@RestController
@Slf4j
public class CacheBlackController {
    @Autowired
    private RedisClient redisClient;

    @GetMapping("/cache/black/get/{key}")
    public String getBlack(@PathVariable String key) {
        return redisClient.get(key);
    }
}
