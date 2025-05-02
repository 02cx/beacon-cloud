package com.dong.strategy.filter.impl;

import com.dong.common.constant.CacheKeyConstant;
import com.dong.common.model.StandardSubmit;
import com.dong.strategy.feign.BeaconCacheFeign;
import com.dong.strategy.filter.StrategyFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 类描述：手机号携号转网
 *
 * @author wuyadong
 * @date 2025/5/2 下午10:59
 */
@Service(value = "transfer")
@Slf4j
public class TransferStrategyFilter implements StrategyFilter {
    // 代表携号转网了！
    private final Boolean TRANSFER = true;

    @Autowired
    private BeaconCacheFeign cacheClient;

    @Override
    public void check(StandardSubmit submit) {
        log.info("【策略模块-携号转网策略】   ing…………");
        //1、获取用户手机号
        String mobile = submit.getMobile();

        //2、直接基于Redis查询携号转网信息
        String value = cacheClient.getBlack(CacheKeyConstant.TRANSFER + mobile);

        //3、如果存在携号转网，设置运营商信息
        if (!Strings.isEmpty(value)) {
            // 代表携号转网了
            submit.setOperatorId(Integer.valueOf(value));
            submit.setIsTransfer(TRANSFER);
            log.info("【策略模块-携号转网策略】   当前手机号携号转网了！");
            return;
        }

        log.info("【策略模块-携号转网策略】   嘛事没有！");

    }
}
