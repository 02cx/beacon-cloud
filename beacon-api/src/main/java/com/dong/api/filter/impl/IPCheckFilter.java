package com.dong.api.filter.impl;

import com.dong.api.filter.CheckFilter;
import com.dong.common.model.StandardSubmit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 类描述：ip地址是否是白名单的检验具体类
 *
 * @author wuyadong
 * @date 2025/4/5 下午10:51
 */
@Component("ip")
@Slf4j
public class IPCheckFilter implements CheckFilter {

    @Override
    public void check(StandardSubmit standardSubmit) {
        log.info("---->ip地址是否是白名单");
    }
}
