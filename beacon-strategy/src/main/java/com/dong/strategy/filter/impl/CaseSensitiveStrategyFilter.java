package com.dong.strategy.filter.impl;

import com.dong.common.model.StandardSubmit;
import com.dong.strategy.filter.StrategyFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 类描述：敏感词过滤器
 *
 * @author wuyadong
 * @date 2025/4/19 下午6:10
 */
@Component("dirtyword")
@Slf4j
public class CaseSensitiveStrategyFilter implements StrategyFilter {
    @Override
    public void check(StandardSubmit standardSubmit) {
        log.info("敏感词过滤器");
    }
}
