package com.dong.test;

import com.dong.test.domain.MobileTransfer;
import com.dong.test.feign.CacheClient;
import com.dong.test.mapper.MobileTransferMapper;
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
 * @date 2025/5/2 下午10:38
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MobileTransferMapperTest {
    @Autowired
    private MobileTransferMapper mapper;

    @Autowired
    private CacheClient cacheClient;

    @Test
    public void findAll() {
        List<MobileTransfer> list = mapper.findAll();

        for (MobileTransfer mobileTransfer : list) {
            cacheClient.set("transfer:" + mobileTransfer.getTransferNumber(), String.valueOf(mobileTransfer.getNowIsp()));
        }
    }
}
