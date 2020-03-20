package com.example.spring.hutool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.spring.hutool.core")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
