package com.example.spring.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.util.UUID;

@SpringBootApplication
public class Application1 {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(Application1.class, args);
    }

    @PostConstruct
    public void init() {
        applicationContext.publishEvent(new MyEvent(this, UUID.randomUUID().toString().replace("-", "")));
    }
}
