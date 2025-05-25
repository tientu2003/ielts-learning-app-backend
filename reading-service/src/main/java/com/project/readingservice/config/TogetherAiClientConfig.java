package com.project.readingservice.config;

import feign.RequestInterceptor;
import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;

public class TogetherAiClientConfig {

    @Value("${together.api.key}")
    private String apiKey;

    @Bean
    public RequestInterceptor togetherAiFeignRequestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Authorization", "Bearer " + apiKey);
            requestTemplate.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        };
    }
}
