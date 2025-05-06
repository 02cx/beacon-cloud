package com.dong.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.test.domain.Channel;
import com.dong.test.service.ChannelService;
import com.dong.test.mapper.ChannelMapper;
import org.springframework.stereotype.Service;

/**
* @author 20117
* @description 针对表【channel(通道表)】的数据库操作Service实现
* @createDate 2025-05-06 09:39:46
*/
@Service
public class ChannelServiceImpl extends ServiceImpl<ChannelMapper, Channel>
    implements ChannelService{

}




