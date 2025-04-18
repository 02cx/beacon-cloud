package com.dong.test.feign;

import com.dong.test.domain.ClientTemplate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/15 下午6:09
 */
@FeignClient(value = "beacon-cache")
public interface CacheClient {

    @PostMapping("/cache/hmset/{key}")
    public void hmset(@PathVariable(value = "key")String key, @RequestBody Map<String,Object> map);

    @PostMapping(value = "/cache/set/{key}")
    public void set(@PathVariable(value = "key")String key, @RequestParam(value = "value")String value);

    @PostMapping(value = "/cache/sadd/{key}")
    public void sadd(@PathVariable(value = "key")String key, @RequestBody Map<String,Object>... value);

    @PostMapping("/cache/template/sadd/{key}")
    public void saddTemplate(@PathVariable(value = "key")String key, @RequestBody Map<String,Object>... value);

    @PostMapping("/cache/template/list/sadd/{key}")
    public void saddTemplateList(@PathVariable(value = "key")String key, @RequestBody List<ClientTemplate> value);

    @PostMapping("/cache/balance/set/{key}")
    public void setBalance(@PathVariable String key, @RequestBody Map<String,Object> clientBalance);
}
