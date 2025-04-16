package com.dong.api.filter.impl;

import com.dong.api.client.BeaconCacheClient;
import com.dong.api.filter.CheckFilter;
import com.dong.api.filter.CheckFilterContext;
import com.dong.common.constant.CacheKeyConstant;
import com.dong.common.enums.ExceptionEnums;
import com.dong.common.exception.ApiException;
import com.dong.common.model.StandardSubmit;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 类描述：ip地址是否是白名单的检验具体类
 *
 * @author wuyadong
 * @date 2025/4/5 下午10:51
 */
@Component("ip")
@Slf4j
public class IPCheckFilter implements CheckFilter {

    @Autowired
    private BeaconCacheClient beaconCacheClient;

    @Override
    public void check(StandardSubmit standardSubmit) {
        Map<String, Object> map = beaconCacheClient.hgetall(CacheKeyConstant.CLIENT_BUSINESS + standardSubmit.getApiKey());
        String ips = (String) map.get("ipAddress");
        List<String> list = Splitter.on(",").omitEmptyStrings().trimResults().splitToList(ips);

        // IP白名单为为空，或者ip地址在白名单中
        if(list.isEmpty() || list.contains(standardSubmit.getRealIp())){
            log.info("ip地址校验通过,{}", standardSubmit.getRealIp());
            return;
        }

        // 否则，抛出异常
        log.info("ip地址校验不通过，{}不合法", standardSubmit.getRealIp());
        throw new ApiException(ExceptionEnums.IP_NOT_WHITE);
    }
}
