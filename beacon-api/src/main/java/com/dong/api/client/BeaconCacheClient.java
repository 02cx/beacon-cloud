package com.dong.api.client;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 类描述：缓存模块的FeignClient
 *
 * @author wuyadong
 * @date 2025/4/15 上午11:48
 */
@FeignClient(value = "beacon-cache")
public class BeaconCacheClient {
}
