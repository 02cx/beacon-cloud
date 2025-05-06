package com.dong.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.test.domain.ClientChannel;
import com.dong.test.service.ClientChannelService;
import com.dong.test.mapper.ClientChannelMapper;
import org.springframework.stereotype.Service;

/**
* @author 20117
* @description 针对表【client_channel(客户通道表)】的数据库操作Service实现
* @createDate 2025-05-06 09:39:59
*/
@Service
public class ClientChannelServiceImpl extends ServiceImpl<ClientChannelMapper, ClientChannel>
    implements ClientChannelService{

}




