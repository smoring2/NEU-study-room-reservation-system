package com.group2.order.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;



@Configuration
@MapperScan("com.group2.order.mapper")
public class OrderConfig {

}
