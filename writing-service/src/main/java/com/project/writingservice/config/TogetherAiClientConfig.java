package com.project.writingservice.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class TogetherAiClientConfig {

    @Value("${together.api.key}")
    private String apiKey;

    @Bean
    public RequestInterceptor togetherAiFeignRequestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Authorization", "Bearer " + apiKey);
        };
    }
}
