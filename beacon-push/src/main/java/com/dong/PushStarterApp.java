package com.dong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/5/11 上午11:15
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PushStarterApp {

    public static void main(String[] args) {
        SpringApplication.run(PushStarterApp.class, args);
    }
}
