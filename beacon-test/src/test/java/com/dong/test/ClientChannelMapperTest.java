package com.dong.test;

import com.dong.test.domain.Channel;
import com.dong.test.domain.ClientChannel;
import com.dong.test.feign.CacheClient;
import com.dong.test.mapper.ChannelMapper;
import com.dong.test.mapper.ClientChannelMapper;
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

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/5/6 上午9:43
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class ClientChannelMapperTest {

    @Autowired
    private ClientChannelMapper clientChannelMapper;

    @Autowired
    private CacheClient cacheClient;

    @Test
    public void test(){
        List<ClientChannel> allClientChannelList = clientChannelMapper.getAllClientChannelList();
        log.info("allClientChannelList:{}",allClientChannelList);
        for(ClientChannel clientChannel : allClientChannelList){
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.convertValue(clientChannel, new  TypeReference<Map<String, Object>>() {});
            cacheClient.sadd("client_channel:" + clientChannel.getClientId(),map);
        }
    }
}
