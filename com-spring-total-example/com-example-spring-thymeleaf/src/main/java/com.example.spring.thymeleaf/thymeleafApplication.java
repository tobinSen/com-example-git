package com.example.spring.thymeleaf;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class thymeleafApplication {

    public static void main(String[] args) {
        //springboot项目不需要webapp，java的Web项目需要webapp
        SpringApplication.run(thymeleafApplication.class, args);
    }

}
