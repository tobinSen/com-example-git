package com.example.spring.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class JwtApplicaton {

    public static void main(String[] args) {
        SpringApplication.run(JwtApplicaton.class, args);
    }
}
