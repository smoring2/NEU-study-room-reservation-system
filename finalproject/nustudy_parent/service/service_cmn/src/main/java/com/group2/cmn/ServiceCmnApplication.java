package com.group2.cmn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Author: Ying Tuo
 * Created Time: $[Date]
 * Description:
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.group2") // set the scan rules; for example, in order to find the Swagger2Config under common/service_util
@EnableDiscoveryClient
public class ServiceCmnApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCmnApplication.class, args);
    }

}
