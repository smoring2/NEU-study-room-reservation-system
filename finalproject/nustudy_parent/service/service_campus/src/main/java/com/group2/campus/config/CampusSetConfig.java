package com.group2.campus.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Author: Ying Tuo
 * Created Time: $[Date]
 * Description:
 */

@Configuration
@MapperScan("com.group2.campus.mapper")
public class CampusSetConfig {
    //Page divider plugin
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
