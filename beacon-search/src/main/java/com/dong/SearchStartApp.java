package com.dong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/5/8 下午4:56
 */
@SpringBootApplication
@EnableDiscoveryClient
public class SearchStartApp {

    public static void main(String[] args) {
        SpringApplication.run(SearchStartApp.class, args);
    }
}
