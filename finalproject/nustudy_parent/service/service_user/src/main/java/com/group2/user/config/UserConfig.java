package com.group2.user.config;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.group2.user.mapper")
public class UserConfig {
}
