package com.uplooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Springboot01Application {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(Springboot01Application.class);
        application.run(args);
    }
}
