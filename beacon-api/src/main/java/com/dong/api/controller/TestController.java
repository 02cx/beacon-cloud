package com.dong.api.controller;

import com.dong.api.filter.CheckFilterContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 类描述： 测试校验链的效果
 *
 * @author wuyadong
 * @date 2025/4/5 下午11:25
 */
@RestController
public class TestController {

    @Autowired
    private CheckFilterContext checkFilterContext;

    @GetMapping("/api/test")
    public void testCheckf(){
        System.out.println("==================================");
        checkFilterContext.check(new Object());
    }
}
