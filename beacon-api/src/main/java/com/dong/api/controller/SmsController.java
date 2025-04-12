package com.dong.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类描述： 映射请求&接收参数&设置响应结果
 *
 * @author wuyadong
 * @date 2025/4/13 上午12:48
 */
@RestController
@RequestMapping("/sms")
public class SmsController {


    @PostMapping("/single_send")
    public void singleSend() {

    }
}
