package com.dong.test;

import com.dong.test.domain.Channel;
import com.dong.test.feign.CacheClient;
import com.dong.test.mapper.ChannelMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.jupiter.api.TestTemplate;
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
public class ChannelMapperTest {

    @Autowired
    private ChannelMapper channelMapper;

    @Autowired
    private CacheClient cacheClient;

    @Test
    public void testGetAllChannelList(){
        List<Channel> allChannelList = channelMapper.getAllChannelList();
        for(Channel channel : allChannelList){
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> map = objectMapper.convertValue(channel, new  TypeReference<Map<String, Object>>() {});
            cacheClient.hmset("channel:" + channel.getId(),map);
        }
    }
}
