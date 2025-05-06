package com.dong.test.mapper;

import com.dong.test.domain.ClientChannel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author 20117
* @description 针对表【client_channel(客户通道表)】的数据库操作Mapper
* @createDate 2025-05-06 09:39:59
* @Entity com.dong.test.domain.ClientChannel
*/
public interface ClientChannelMapper extends BaseMapper<ClientChannel> {

    List<ClientChannel> getAllClientChannelList();
}




