package com.dong.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

/**
 * 类描述：缓存模块的FeignClient
 *
 * @author wuyadong
 * @date 2025/4/15 上午11:48
 */
@FeignClient(value = "beacon-cache")
public interface BeaconCacheClient {

    @GetMapping("/cache/hgetall/{key}")
    public Map<String, Object> hgetall(@PathVariable(value = "key")String key);

    @GetMapping("/cache/sign/smembers/{key}")
    public Set<Map> smembers(@PathVariable(value = "key")String key);

    @GetMapping("/cache/template/smembers/{key}")
    public Set<Map> smembersTemplate(@PathVariable(value = "key")String key);

    @GetMapping("/cache/balancce/hget/{key}")
    public Long hget(@PathVariable String key,@RequestParam String field);
}
