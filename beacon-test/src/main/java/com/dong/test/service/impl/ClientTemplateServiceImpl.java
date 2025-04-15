package com.dong.test.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dong.test.domain.ClientTemplate;
import com.dong.test.service.ClientTemplateService;
import com.dong.test.mapper.ClientTemplateMapper;
import org.springframework.stereotype.Service;

/**
* @author 20117
* @description 针对表【client_template(短信模板)】的数据库操作Service实现
* @createDate 2025-04-15 16:36:05
*/
@Service
public class ClientTemplateServiceImpl extends ServiceImpl<ClientTemplateMapper, ClientTemplate>
    implements ClientTemplateService{

}




