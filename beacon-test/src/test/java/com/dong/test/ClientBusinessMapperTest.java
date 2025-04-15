package com.dong.test;

import com.dong.test.domain.ClientBusiness;
import com.dong.test.feign.CacheClient;
import com.dong.test.mapper.ClientBusinessMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/15 下午5:28
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ClientBusinessMapperTest {

    @Autowired
    private ClientBusinessMapper clientBusinessMapper;

    @Autowired
    private CacheClient cacheClient;

    /**
     * 根据clientId查询
     */
    @Test
    public void testSelectByClientId() {
        ClientBusiness clientBusiness = clientBusinessMapper.selectByClientId(1L);
        ObjectMapper objectMapper = new ObjectMapper();
        // 单步完成对象到 Map 的转换
        Map<String, Object> map = objectMapper.convertValue(clientBusiness, new  TypeReference<Map<String, Object>>() {});
        log.info("map:{}", map);
        cacheClient.hmset("client_business:" + clientBusiness.getApikey(), map);
    }
    

}
