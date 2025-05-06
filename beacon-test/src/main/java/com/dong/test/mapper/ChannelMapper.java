package com.dong.test.mapper;

import com.dong.test.domain.Channel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 20117
* @description 针对表【channel(通道表)】的数据库操作Mapper
* @createDate 2025-05-06 09:39:46
* @Entity com.dong.test.domain.Channel
*/
public interface ChannelMapper extends BaseMapper<Channel> {

    List<Channel> getAllChannelList();
}




