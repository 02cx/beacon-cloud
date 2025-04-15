package com.dong.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dong.test.domain.ClientTemplate;
import com.dong.test.feign.CacheClient;
import com.dong.test.mapper.ClientTemplateMapper;
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
 * @date 2025/4/15 下午7:43
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class ClientTemplateMapperTest {

    @Autowired
    private CacheClient cacheClient;

    @Autowired
    private ClientTemplateMapper clientTemplateMapper;

    @Test
    public void testTemplateSadd() {
        LambdaQueryWrapper<ClientTemplate> objectLambdaQueryWrapper = new LambdaQueryWrapper<>();
        objectLambdaQueryWrapper.eq(ClientTemplate::getSignId, 3L);
        List<ClientTemplate> clientTemplates = clientTemplateMapper.selectList(objectLambdaQueryWrapper);

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Object>> value = clientTemplates.stream().map(clientTemplate -> {
            return objectMapper.convertValue(clientTemplate, new TypeReference<Map<String, Object>>() {
            });
        }).collect(Collectors.toList());

        cacheClient.saddTemplate("client_template:3",value.toArray(new Map[]{}));



    }
}
