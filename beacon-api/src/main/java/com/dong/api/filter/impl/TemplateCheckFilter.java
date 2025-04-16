package com.dong.api.filter.impl;

import com.dong.api.client.BeaconCacheClient;
import com.dong.api.filter.CheckFilter;
import com.dong.api.util.MsgTemplateConvert;
import com.dong.common.constant.CacheKeyConstant;
import com.dong.common.enums.ExceptionEnums;
import com.dong.common.exception.ApiException;
import com.dong.common.model.StandardSubmit;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 类描述：短信的模板的检验具体类
 *
 * @author wuyadong
 * @date 2025/4/5 下午10:51
 */
@Component("template")
@Slf4j
public class TemplateCheckFilter implements CheckFilter {

    @Autowired
    private BeaconCacheClient beaconCacheClient;

    @Override
    public void check(StandardSubmit standardSubmit) {
        // 拿到客户端的模板
        String text = standardSubmit.getText();
        Long signId = standardSubmit.getSignId();
        // 获取客户的缓存模板
        Set<Map> cachedTemplates = beaconCacheClient.smembersTemplate(CacheKeyConstant.CLIENT_TEMPLATE + signId);
        if(Strings.isEmpty(text) || cachedTemplates == null || cachedTemplates.isEmpty()) {
            log.error("【模板校验】 缓存中的模板 = {} 不存在", text);
            throw new ApiException(ExceptionEnums.ERROR_TEMPLATE);
        }

        // 【sign】您的验证码是12345。如非本人操作，请忽略本短信--->您的验证码是12345。如非本人操作，请忽略本短信
        // 移除【xxx】前缀
        String template = text.replaceFirst("^【[^】]*】", "");

        // 3. 校验模板是否存在（根据业务需求调整匹配逻辑）
        boolean isValid = cachedTemplates.stream()
                .anyMatch(tpl -> {
                    String dbTemplate = (String) tpl.get("templateText");
                    String regex = MsgTemplateConvert.convertTemplateToRegex(dbTemplate);
                    log.info("【模板校验】 将数据库中的模板转为 = {}", regex);

                    boolean match = Pattern.matches(regex, template);
                    // 新增调试日志
                    log.debug("数据库模板: {} → 生成正则: {}", dbTemplate, regex);
                    log.debug("客户端内容: {} → 匹配结果: {}", template, match);

                    return match;
                });

        if (!isValid) {
            log.error("【模板校验】 缓存中的模板 = {} 校验不通过", text);
            throw new ApiException(ExceptionEnums.ERROR_TEMPLATE);
        }
        log.info("【模板校验】 缓存中的模板 = {} 校验通过", text);

    }
}
