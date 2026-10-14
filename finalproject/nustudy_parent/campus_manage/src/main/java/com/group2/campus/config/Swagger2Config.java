package com.group2.campus.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger2
 */
@Configuration
public class Swagger2Config {

    @Bean
    public GroupedOpenApi webApiConfig(){

        return GroupedOpenApi.builder()
                .group("webApi")
                //Filter out all pages under the admin path
                .pathsToMatch("/P2P/**")
                .addOpenApiCustomizer(openApi -> openApi.info(webApiInfo().getInfo()))
                .build();

    }

    private OpenAPI webApiInfo(){

        return new OpenAPI()
                .info(new Info()
                        .title("Website - API Documentation")
                        .description("This document describes the website microservice interface definition")
                        .version("1.0")
                        .contact(new Contact().name("qy").url("http://atguigu.com").email("55317332@qq.com")));
    }


}
