package com.dong.test;

import com.dong.test.domain.MobileAreaDTO;
import com.dong.test.feign.CacheClient;
import com.dong.test.mapper.MobileAreaMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/19 下午6:34
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MoblieAreaMapperTest {

    @Autowired
    private MobileAreaMapper mobileAreaMapper;

    @Autowired
    private CacheClient cacheClient;

    @Test
    public void test() {
        List<MobileAreaDTO> list = mobileAreaMapper.findAll();
        Map map = new HashMap(list.size());
        for (MobileAreaDTO mobileArea : list) {
            map.put("phase:" + mobileArea.getMobileNumber(),mobileArea.getMobileArea() + "," + mobileArea.getMobileType());
        }
        cacheClient.pipelineString(map);
    }

}
