package com.dong.strategy.filter.impl;

import com.dong.common.enums.ExceptionEnums;
import com.dong.common.exception.DirtyWordException;
import com.dong.common.model.StandardSubmit;
import com.dong.strategy.filter.StrategyFilter;
import com.dong.strategy.util.HutoolDFAUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 类描述：敏感词过滤器
 *
 * @author wuyadong
 * @date 2025/4/19 下午6:10
 */
@Component("dirtyword")
@Slf4j
public class CaseSensitiveStrategyFilter implements StrategyFilter {
    @Override
    public void check(StandardSubmit standardSubmit) {
        log.info("敏感词过滤器");
        // 获取短信内容
        String text = standardSubmit.getText();

        // 调用DFA查看敏感词
        List<String> dirtyWords = HutoolDFAUtil.getDirtyWords(text);
        log.info("敏感词比对后的列表：{}", dirtyWords);

        if (dirtyWords != null && dirtyWords.size() > 0) {
            // 存在敏感词 抛出异常/其他处理
            throw new DirtyWordException(ExceptionEnums.DIRTWORD_EXIST);
        }
    }
}
