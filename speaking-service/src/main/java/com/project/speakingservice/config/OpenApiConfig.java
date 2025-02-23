package com.project.speakingservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("Speaking Service")
                        .description("This is Rest API for Reading Service")
                        .version("v0.1")
                        .license(new License().name("Apache 2.0"))
                );
    }
}
