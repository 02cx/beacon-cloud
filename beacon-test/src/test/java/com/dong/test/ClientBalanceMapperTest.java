package com.dong.test;

import com.dong.test.domain.ClientBalance;
import com.dong.test.feign.CacheClient;
import com.dong.test.mapper.ClientBalanceMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/15 下午9:20
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ClientBalanceMapperTest {

    @Autowired
    private CacheClient cacheClient;

    @Autowired
    private ClientBalanceMapper clientBalanceMapper;

    @Test
    public void test(){
        ClientBalance clientBalance = clientBalanceMapper.selectById(1L);
        cacheClient.setBalance("client_balance:1",clientBalance.getBalance().toString());
    }
}
