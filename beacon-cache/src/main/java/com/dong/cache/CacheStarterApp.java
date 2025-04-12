package com.dong.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.io.File;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/12 下午5:04
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CacheStarterApp {

    public static void main(String[] args) {
        SpringApplication.   run(CacheStarterApp.class, args);
    }

}
