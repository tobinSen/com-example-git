package com.example.spring.mongodb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.UUID;

@SpringBootApplication
@RestController
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

    //匹配正则 + ant表达式
    @RequestMapping(value = "/test/{xxx:.+}", method = RequestMethod.GET)
    public String xxx(@PathVariable(value = "xxx") String xxx) {
        try {
            return xxx;
        } catch (Exception e) {

        }
        return xxx;
    }
}
