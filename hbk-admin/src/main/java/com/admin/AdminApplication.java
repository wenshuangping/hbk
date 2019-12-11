package com.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wsp 21
 * @date 2017年10月27日13:59:05
 */
@EnableFeignClients
@SpringCloudApplication
@ComponentScan(basePackages = {"com.admin", "com.github.common.bean"})
public class AdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}