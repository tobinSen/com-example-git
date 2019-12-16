package com.example.spring.log4j2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Log4j2Application {

    public static void main(String[] args) {
        SpringApplication.run(Log4j2Application.class, args);
    }

}
