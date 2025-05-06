package com.dong.strategy.feign;

import com.dong.common.domain.MobileAreaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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


    @GetMapping("/cache/black/get/{key}")
    public String getBlack(@PathVariable String key);

    @PostMapping("/cache/ratelimiter/zadd/{key}/{score}/{member}")
    public Boolean zadd(@PathVariable(value = "key")String key, @PathVariable(value = "score")Long score, @PathVariable(value = "member")String member);

    @GetMapping("/cache/ratelimiter/zrange/{key}/{start}/{end}")
    public Integer zrangeByScore(@PathVariable(value =  "key") String key,@PathVariable(value = "start") Double start, @PathVariable(value = "end") Double end);

    @PostMapping("/cache/ratelimiter/zremove/{key}/{member}")
    public void zRemove(@PathVariable(value = "key")String key, @PathVariable(value = "member")String member);

    @PostMapping("/cache/hIncrBy/{key}/{field}/{delta}")
    public Long hIncrBy(@PathVariable String key,@PathVariable String  field, @PathVariable Long delta);



}
