package com.example.spring.git;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class GitApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitApplication.class, args);
    }


    @Autowired
    private Environment environment;

    @PostConstruct
    public void init(){
        StandardEnvironment standardEnv = (StandardEnvironment) environment;
        String property = standardEnv.getProperty("boss.java");

    }
}
