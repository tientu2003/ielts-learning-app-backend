package com.project.speakingservice.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http .cors(Customizer.withDefaults())
            .authorizeHttpRequests(auth ->
                auth.requestMatchers("public/api/speaking/**",
                            "swagger-ui/**",
                            "swagger-ui**",
                            "/v3/api-docs/**",
                                "/v3/api-docs**").permitAll()
                .anyRequest()
                .authenticated()
            ).oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }
}
