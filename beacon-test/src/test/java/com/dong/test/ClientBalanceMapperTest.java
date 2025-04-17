package com.dong.test;

import com.dong.test.domain.ClientBalance;
import com.dong.test.feign.CacheClient;
import com.dong.test.mapper.ClientBalanceMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.sun.media.jfxmedia.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/15 下午9:20
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ClientBalanceMapperTest {

    @Autowired
    private CacheClient cacheClient;

    @Autowired
    private ClientBalanceMapper clientBalanceMapper;

    @Test
    public void test2(){
        ClientBalance clientBalance = clientBalanceMapper.selectById(1L);
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String, Object> map = objectMapper.convertValue(clientBalance, new  TypeReference<Map<String, Object>>() {});
        log.info("存储key = {}，存储value = {}", "client_balance:1", map);
        cacheClient.setBalance("client_balance:1",map);
    }



}
