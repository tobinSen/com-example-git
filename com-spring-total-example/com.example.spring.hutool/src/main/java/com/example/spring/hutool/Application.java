package com.example.spring.hutool;

import com.example.spring.hutool.listener.MyApplicationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import java.util.UUID;

@SpringBootApplication
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "xxx")
@ConstructorBinding
public class Application {

    private String name;

    public Application(String name) {
        this.name = name;
    }

    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    @PostConstruct
    public void init(){
        context.publishEvent(new MyApplicationEvent(this, UUID.randomUUID().toString()));
    }



}
