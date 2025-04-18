package com.dong.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * 类描述：api模块启动类
 *
 * @author wuyadong
 * @date 2025/4/4 上午12:42
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.dong.api", "com.dong.common"})
public class ApiStartApp {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ApiStartApp.class, args);

    }
}
