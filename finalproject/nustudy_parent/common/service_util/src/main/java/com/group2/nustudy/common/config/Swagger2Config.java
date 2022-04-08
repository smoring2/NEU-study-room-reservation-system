package com.group2.nustudy.common.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2 config
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    @Bean
    public Docket webApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.and(PathSelectors.regex("/api/.*")))
                .build();

    }

    @Bean
    public Docket adminApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("adminApi")
                .apiInfo(adminApiInfo())
                .select()
                .paths(Predicates.and(PathSelectors.regex("/admin/.*")))
                .build();

    }

    private ApiInfo webApiInfo(){

        return new ApiInfoBuilder()
                .title("Web-API Documentation")
                .description("This doc description the frontend API of NU-Study microservice system")
                .version("1.0")
                .contact(new Contact("Group2", "https://www.northeastern.edu", "" ))
                .build();
    }

    private ApiInfo adminApiInfo(){

        return new ApiInfoBuilder()
                .title("Backend Management system-API Documentation")
                .description("This doc description the backend API of NU-Study microservice system")
                .version("1.0")
                .contact(new Contact("Group2", "https://www.northeastern.edu", "" ))
                .build();
    }
}
