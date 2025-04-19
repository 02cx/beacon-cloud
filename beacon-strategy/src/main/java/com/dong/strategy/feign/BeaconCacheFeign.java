package com.dong.strategy.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/19 下午5:58
 */
@FeignClient(value = "beacon-cache")
public interface BeaconCacheFeign {
    @GetMapping("/cache/balance/hgetfilters/{key}/{field}")
    public String hgetFilters(@PathVariable String key, @PathVariable String field);
}
