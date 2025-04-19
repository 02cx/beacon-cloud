package com.dong.strategy.filter.impl;

import com.dong.common.model.StandardSubmit;
import com.dong.strategy.filter.StrategyFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 类描述：黑名单过滤策略
 *
 * @author wuyadong
 * @date 2025/4/19 下午6:12
 */
@Component("black")
@Slf4j
public class BlackListStrategyFilter implements StrategyFilter {
    @Override
    public void check(StandardSubmit standardSubmit) {
        log.info("黑名单过滤策略");
    }
}
