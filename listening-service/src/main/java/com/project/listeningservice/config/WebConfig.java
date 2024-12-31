package com.project.listeningservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") // Áp dụng cho tất cả các endpoint
//                    .allowedOrigins("http://localhost:3000") // Chỉ cho phép từ nguồn này
//                    .allowedMethods("GET", "POST", "PUT") // Cho phép các method
//                    .allowedHeaders("*") // Cho phép tất cả các header
//                    .exposedHeaders("Authorization") // Các header được trả về (nếu cần)
//                    .allowCredentials(true) // Cho phép sử dụng cookie hoặc xác thực
//                    .maxAge(3600); // Thời gian cache của preflight request (giây)
//    }
}
