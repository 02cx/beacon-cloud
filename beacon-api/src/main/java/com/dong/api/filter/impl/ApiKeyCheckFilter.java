package com.dong.api.filter.impl;

import com.dong.api.client.BeaconCacheClient;
import com.dong.api.filter.CheckFilter;
import com.dong.common.constant.CacheKeyConstant;
import com.dong.common.enums.ExceptionEnums;
import com.dong.common.exception.ApiException;
import com.dong.common.model.StandardSubmit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 类描述：apiKey的检验具体类
 *
 * @author wuyadong
 * @date 2025/4/5 下午10:51
 */
@Component("apikey")
@Slf4j
public class ApiKeyCheckFilter implements CheckFilter {

    @Autowired
    private BeaconCacheClient beaconCacheClient;

    @Override
    public void check(StandardSubmit standardSubmit) {
        Map<String, Object> map = beaconCacheClient.hgetall(CacheKeyConstant.CLIENT_BUSINESS + standardSubmit.getApiKey());
        log.info("【OpenFeign调用】 apiKey = {}", standardSubmit.getApiKey());
        if (map == null || map.isEmpty()) {
            log.error("【apiKey校验】 apiKey = {} 不存在", standardSubmit.getApiKey());
            throw new ApiException(ExceptionEnums.ERROR_APIKEY);
        }

        // 正常封装数据
        standardSubmit.setClientId(Long.parseLong(map.get("id") + ""));
        log.info("【apiKey校验成功】 apiKey = {} 存在", standardSubmit.getApiKey());

    }
}
