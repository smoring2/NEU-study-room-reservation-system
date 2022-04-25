package com.group2.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.group2"})
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.group2"})
public class ServiceOrderApplication {


    public static void main(String[] args) {
        String path = "src/main/resources/all.policy";
//        System.setProperty("java.security.policy", path);
        SpringApplication.run(ServiceOrderApplication.class, args);
    }
}

