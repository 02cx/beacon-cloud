package com.dong.test;

import com.dong.test.feign.CacheClient;
import com.dong.test.mapper.MobileDirtywordMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/27 上午11:54
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MobileDirtywordMapperTest {

    @Autowired
    private CacheClient cacheClient;

    @Autowired
    private MobileDirtywordMapper mobileDirtywordMapper;


    @Test
    public void sadd(){
        List<String> dirtyWord = mobileDirtywordMapper.findDirtyword();
        cacheClient.sadd("dirty_word",dirtyWord.toArray(new String[]{}));
    }
}
