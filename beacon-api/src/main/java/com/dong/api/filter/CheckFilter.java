package com.dong.api.filter;

/**
 * 类描述： 单条短信接口模块的校验项
 *
 * @author wuyadong
 * @date 2025/4/5 下午10:47
 */
public interface CheckFilter {

    /**
     * 校验
     * @param obj
     */
    void check(Object obj);
}
