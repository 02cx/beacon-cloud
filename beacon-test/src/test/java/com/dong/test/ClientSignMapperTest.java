package com.dong.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dong.test.domain.ClientSign;
import com.dong.test.feign.CacheClient;
import com.dong.test.mapper.ClientSignMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
 * @date 2025/4/15 下午6:24
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ClientSignMapperTest {
    
    @Autowired
    private CacheClient cacheClient;

    @Autowired
    private ClientSignMapper clientSignMapper;

    /**
     * 根据客户id查询客户签约信息
     */
    @Test
    public void findByClientId() {

        LambdaQueryWrapper<ClientSign> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(ClientSign::getClientId, 1L);
        List<ClientSign> list = clientSignMapper.selectList(lambdaQueryWrapper);

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> value = list.stream()
                .map(cs -> objectMapper.convertValue(cs, new TypeReference<Map<String, Object>>() {}))
                .collect(Collectors.toList());
        cacheClient.sadd("client_sign:1L", value.toArray(new Map[]{}));
    }
}
