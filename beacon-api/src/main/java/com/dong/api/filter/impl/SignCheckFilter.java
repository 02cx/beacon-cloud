package com.dong.api.filter.impl;

import com.dong.api.filter.CheckFilter;
import com.dong.common.model.StandardSubmit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 类描述：短信的签名的检验具体类
 *
 * @author wuyadong
 * @date 2025/4/5 下午10:51
 */
@Component("sign")
@Slf4j
public class SignCheckFilter implements CheckFilter {

    @Override
    public void check(StandardSubmit standardSubmit) {
        log.info("---->短信的签名校验");
    }
}
