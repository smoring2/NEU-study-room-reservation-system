package com.group2.nustudy.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger2 config
 */
@Configuration
public class Swagger2Config {

    @Bean
    public GroupedOpenApi webApiConfig(){

        return GroupedOpenApi.builder()
                .group("webApi")
                .pathsToMatch("/api/**")
                .addOpenApiCustomizer(openApi -> openApi.info(webApiInfo().getInfo()))
                .build();

    }

    @Bean
    public GroupedOpenApi adminApiConfig(){

        return GroupedOpenApi.builder()
                .group("adminApi")
                .pathsToMatch("/admin/**")
                .addOpenApiCustomizer(openApi -> openApi.info(adminApiInfo().getInfo()))
                .build();

    }

    private OpenAPI webApiInfo(){

        return new OpenAPI()
                .info(new Info()
                        .title("Web-API Documentation")
                        .description("This doc description the frontend API of NU-Study microservice system")
                        .version("1.0")
                        .contact(new Contact().name("Group2").url("https://www.northeastern.edu").email("")));
    }

    private OpenAPI adminApiInfo(){

        return new OpenAPI()
                .info(new Info()
                        .title("Backend Management system-API Documentation")
                        .description("This doc description the backend API of NU-Study microservice system")
                        .version("1.0")
                        .contact(new Contact().name("Group2").url("https://www.northeastern.edu").email("")));
    }
}
