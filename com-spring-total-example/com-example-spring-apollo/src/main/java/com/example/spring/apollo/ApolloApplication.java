package com.example.spring.apollo;

import com.example.spring.apollo.config.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class ApolloApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApolloApplication.class, args);
    }

    @Autowired
    private Person person;

    @PostConstruct
    public void init(){
        System.out.println(person.toString());
    }
}
