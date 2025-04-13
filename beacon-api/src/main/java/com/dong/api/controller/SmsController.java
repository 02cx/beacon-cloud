package com.dong.api.controller;

import com.dong.api.form.SingleSendForm;
import com.dong.api.util.R;
import com.dong.api.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类描述： 映射请求&接收参数&设置响应结果
 *
 * @author wuyadong
 * @date 2025/4/13 上午12:48
 */
@Slf4j
@RestController
@RequestMapping("/sms")
public class SmsController {


    @PostMapping(value = "/single_send",produces = "application/json;charset=utf-8")
    public ResultVO singleSend(@RequestBody @Validated SingleSendForm singleSendForm) {
        log.info("singleSendForm:{}", singleSendForm);
        return R.ok();
    }
}
