package com.group2.campus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Author: Ying Tuo
 * Created Time: $[Date]
 * Description:
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.group2") // set the scan rules; for example, in order to find the Swagger2Config under common/service_util
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.group2")
public class ServiceCampusApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCampusApplication.class, args);
    }

}
