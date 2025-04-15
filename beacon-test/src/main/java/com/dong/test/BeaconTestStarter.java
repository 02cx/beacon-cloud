package com.dong.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 类描述：
 *
 * @author wuyadong
 * @date 2025/4/15 下午3:51
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BeaconTestStarter {

    public static void main(String[] args) {
        SpringApplication.run(BeaconTestStarter.class, args);
    }
}
