package com.dong.strategy.util;

import cn.hutool.dfa.WordTree;
import com.dong.strategy.feign.BeaconCacheFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/27 下午12:40
 */
@Slf4j
public class HutoolDFAUtil {

    private static WordTree wordTree = new WordTree();

    static{
        // 获取spring容器中的cacheFeignClient
        BeaconCacheFeign cacheClient = SpringContextHolder.getBean(BeaconCacheFeign.class);
        Set<String> dirtyWords = cacheClient.smembers("dirty_word");
        log.info("从redis缓存中加载敏感词完成，构成敏感词树：{}", dirtyWords);
        // 构建dfa的敏感词树
        wordTree.addWords(dirtyWords);
    }

    /**
     * 匹配敏感词
     * @param text
     * @return
     */
    public static List<String> getDirtyWords(String text){
        return wordTree.matchAll(text);
    }
}
