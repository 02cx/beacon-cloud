package com.dong.cache.controller;

import com.dong.cache.domain.ClientTemplate;
import com.msb.framework.redis.RedisClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/15 下午7:38
 */
@RestController
@Slf4j
public class CacheTemplateController {

    @Autowired
    private RedisClient redisClient;

    @PostMapping("/cache/template/sadd/{key}")
    public void saddTemplate(@PathVariable(value = "key")String key, @RequestBody Map<String,Object>... value) {
        redisClient.sAdd(key, value);
    }

    @PostMapping("/cache/template/list/sadd/{key}")
    public void saddTemplateList(@PathVariable(value = "key")String key, @RequestBody List<ClientTemplate> value) {
        redisClient.sAdd(key, value);
    }


}
