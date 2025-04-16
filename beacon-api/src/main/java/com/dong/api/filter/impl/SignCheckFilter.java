package com.dong.api.filter.impl;

import com.dong.api.client.BeaconCacheClient;
import com.dong.api.filter.CheckFilter;
import com.dong.common.constant.CacheKeyConstant;
import com.dong.common.enums.ExceptionEnums;
import com.dong.common.exception.ApiException;
import com.dong.common.model.StandardSubmit;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * 类描述：短信的签名的检验具体类
 *
 * @author wuyadong
 * @date 2025/4/5 下午10:51
 */
@Component("sign")
@Slf4j
public class SignCheckFilter implements CheckFilter {

    @Autowired
    private BeaconCacheClient beaconCacheClient;

    @Override
    public void check(StandardSubmit standardSubmit) {

        // 从客户端提交的text中获取签名信息
        String text = standardSubmit.getText();
        String sign = Splitter.onPattern("[【】]+")  // 按中括号分割
                .omitEmptyStrings()      // 忽略空字符串
                .splitToList(text)
                .get(0);                 // 取第一个非空结果
        log.info("【签名校验】 从客户端提交的text中获取签名信息 = {}", sign);
        if(Strings.isEmpty(sign)){
            log.error("【签名校验】 签名 = {} 不存在，客户提交的文本 = {}", sign,text);
            throw new ApiException(ExceptionEnums.ERROR_SIGN);
        }
        // 从缓存中拿到客户的签名信息
        Set<Map> smembers = beaconCacheClient.smembers(CacheKeyConstant.CLIENT_SIGN + standardSubmit.getClientId());
        if(smembers.isEmpty()){
            log.error("【签名校验】 缓存中的签名 = {} 不存在", sign);
            throw new ApiException(ExceptionEnums.ERROR_SIGN);
        }

        // 比对客户提交的签名与缓存中的签名是否一致
        for(Map member : smembers){
            if(sign.equals(member.get("signInfo"))){
                log.info("【签名校验】 签名 = {} 存在", sign);
                return;
            }
        }
        log.error("【签名校验】 签名比对不通过 = {}", sign);
        throw new ApiException(ExceptionEnums.ERROR_SIGN);

    }
}
