package com.dong.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.test.domain.ClientBusiness;
import com.dong.test.service.ClientBusinessService;
import com.dong.test.mapper.ClientBusinessMapper;
import org.springframework.stereotype.Service;

/**
* @author 20117
* @description 针对表【client_business(客户信息表)】的数据库操作Service实现
* @createDate 2025-04-15 16:22:37
*/
@Service
public class ClientBusinessServiceImpl extends ServiceImpl<ClientBusinessMapper, ClientBusiness>
    implements ClientBusinessService{

}




