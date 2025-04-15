package com.dong.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.test.domain.ClientBalance;
import com.dong.test.service.ClientBalanceService;
import com.dong.test.mapper.ClientBalanceMapper;
import org.springframework.stereotype.Service;

/**
* @author 20117
* @description 针对表【client_balance(客户余额表)】的数据库操作Service实现
* @createDate 2025-04-15 17:03:04
*/
@Service
public class ClientBalanceServiceImpl extends ServiceImpl<ClientBalanceMapper, ClientBalance>
    implements ClientBalanceService{

}




