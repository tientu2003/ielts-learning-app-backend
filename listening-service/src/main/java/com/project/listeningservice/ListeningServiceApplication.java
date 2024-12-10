package com.project.listeningservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableTransactionManagement
@EnableDiscoveryClient
public class ListeningServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ListeningServiceApplication.class, args);
    }

}
