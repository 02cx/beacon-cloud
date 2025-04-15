package com.dong.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.test.domain.ClientSign;
import com.dong.test.service.ClientSignService;
import com.dong.test.mapper.ClientSignMapper;
import org.springframework.stereotype.Service;

/**
* @author 20117
* @description 针对表【client_sign(短信签名认证表)】的数据库操作Service实现
* @createDate 2025-04-15 16:23:53
*/
@Service
public class ClientSignServiceImpl extends ServiceImpl<ClientSignMapper, ClientSign>
    implements ClientSignService{

}




