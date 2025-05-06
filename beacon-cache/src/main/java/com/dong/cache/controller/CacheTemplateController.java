package com.dong.cache.controller;

import com.dong.cache.domain.Channel;
import com.dong.cache.domain.ClientTemplate;
import com.msb.framework.redis.RedisClient;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @GetMapping("/cache/template/smembers/{key}")
    public Set<Map> smembersTemplate(@PathVariable(value = "key")String key) {
        return redisClient.sMembers(key);
    }


    @PostMapping("/cache/chennal/list/sadd/{key}")
    public void saddChannelList(@PathVariable(value = "key")String key, @RequestBody List<Channel> value) {
        redisClient.sAdd(key, value);
    }


    @GetMapping("/cache/channel/get/{key}")
    public Map getChannel(String key){
        return redisClient.hGetAll(key);
    }

}
