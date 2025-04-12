package com.dong.api.filter.impl;

import com.dong.api.filter.CheckFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 类描述：apiKey的检验具体类
 *
 * @author wuyadong
 * @date 2025/4/5 下午10:51
 */
@Component("apikey")
@Slf4j
public class ApiKeyCheckFilter implements CheckFilter {

    @Override
    public void check(Object obj) {
        log.info("---->apiKey的校验");
    }
}
