package com.dong.strategy.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/27 下午1:20
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    // 按名称获取 Bean（新增）
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    // 按类型获取 Bean（保留原有方法）
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }
}
