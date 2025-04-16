package com.dong.api.filter;

import com.dong.common.model.StandardSubmit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;

/**
 * 类描述：单条短信的校验上下文
 *
 * @author wuyadong
 * @date 2025/4/5 下午11:02
 */
@Component
@RefreshScope
@Slf4j
public class CheckFilterContext {

    // Spring的IOC会将对象都放入到Map集合
    @Autowired
    private Map<String, CheckFilter> checkFiltersMap;

    // nacos配置文件中配置的校验内容和顺序
    @Value("${filters}")
    private String filters;

    /**
     *  将object中的内容 按配置的校验顺序依次校验
     * @param
     */
    public void check(StandardSubmit standardSubmit) {
       // 将配置的内容按“，”拆分
        String[] filterNames = filters.split(",");
        log.info("【校验顺序】 = {}", filterNames.toString());
        Arrays.stream(filterNames).forEach(filter -> {
            checkFiltersMap.get(filter).check(standardSubmit);
        });
    }
}
