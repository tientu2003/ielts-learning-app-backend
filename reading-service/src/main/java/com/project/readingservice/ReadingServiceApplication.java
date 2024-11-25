package com.project.readingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ReadingServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadingServiceApplication.class, args);
    }

}
