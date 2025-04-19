package com.dong.strategy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/19 下午5:15
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class StrategyStarterApp {

    public static void main(String[] args) {
        SpringApplication.run(StrategyStarterApp.class, args);
    }
}
