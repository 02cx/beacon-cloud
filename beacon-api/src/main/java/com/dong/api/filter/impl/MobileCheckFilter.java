package com.dong.api.filter.impl;

import com.dong.api.filter.CheckFilter;
import com.dong.api.util.PhoneFormatCheckUtil;
import com.dong.common.enums.ExceptionEnums;
import com.dong.common.exception.ApiException;
import com.dong.common.model.StandardSubmit;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

/**
 * 类描述：手机号的格式合法性的检验具体类
 *
 * @author wuyadong
 * @date 2025/4/5 下午10:51
 */
@Component("mobile")
@Slf4j
public class MobileCheckFilter implements CheckFilter {

    @Override
    public void check(StandardSubmit standardSubmit) {
        String mobile = standardSubmit.getMobile();
        if(!Strings.isEmpty(mobile) && PhoneFormatCheckUtil.isChinaPhone(mobile)){
            // 如果校验进来，代表手机号么得问题
            log.info("【接口模块-校验手机号】   手机号格式合法 mobile = {}",mobile);
            return;
        }
        log.error("【接口模块-校验手机号】   手机号格式不正确 mobile = {}",mobile);
        throw new ApiException(ExceptionEnums.ERROR_MOBILE);
    }
}
