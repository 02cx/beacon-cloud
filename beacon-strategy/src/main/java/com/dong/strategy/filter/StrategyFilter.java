package com.dong.strategy.filter;

import com.dong.common.model.StandardSubmit;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/19 下午5:59
 */
public interface StrategyFilter {

    void check(StandardSubmit standardSubmit);
}
