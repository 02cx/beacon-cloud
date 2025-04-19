package com.dong.strategy.filter;

import com.dong.common.constant.CacheKeyConstant;
import com.dong.common.model.StandardSubmit;
import com.dong.strategy.feign.BeaconCacheFeign;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 类描述：过滤责任链的上下文
 *
 * @author wuyadong
 * @date 2025/4/19 下午6:14
 */
@Component
@Slf4j
public class StrategyFilterContext {

    @Autowired
    private Map<String,StrategyFilter> strategyFilterMap;

    @Autowired
    private BeaconCacheFeign beaconCacheFeign;

    /**
     * 过滤责任链
     * @param standardSubmit
     */
    public void strategy(StandardSubmit standardSubmit){
        // 基于redis获取客户的动态校验规则
        String clientFilters = beaconCacheFeign.hgetFilters(CacheKeyConstant.CLIENT_BUSINESS + standardSubmit.getApiKey(), "clientFilters");
        List<String> list = Splitter.on(",").trimResults().omitEmptyStrings().splitToList(clientFilters);
        log.info("客户端动态校验规则：{}",list);
        if(list != null && !list.isEmpty()){
            for(String filter : list){
                StrategyFilter strategyFilter = strategyFilterMap.get(filter);
                if(strategyFilter != null){
                    strategyFilter.check(standardSubmit);
                }
            }
        }

    }

}
