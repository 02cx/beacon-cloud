package com.dong.strategy.feign;

import com.dong.common.domain.MobileAreaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;

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

    @GetMapping("/cache/phase/get/{key}")
    public MobileAreaDTO getPhase(@PathVariable String key);

    @GetMapping("/cache/dirtyword/smembers/{key}")
    public Set<String> smembers(@PathVariable String key);


    @GetMapping("/cache/hget/isCallback/{key}/{field}")
    public Integer hgetIsCallback(@PathVariable(value = "key") String key, @PathVariable(value = "field") String field);

    @GetMapping("/cache/hget/callbackUrl/{key}/{field}")
    public String hgetCallbackUrl(@PathVariable(value = "key")String key, @PathVariable(value = "field")String field);



}
