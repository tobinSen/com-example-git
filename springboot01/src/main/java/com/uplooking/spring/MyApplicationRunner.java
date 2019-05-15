package com.uplooking.spring;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationRunner implements ApplicationRunner {

    public MyApplicationRunner(SpringApplication application ,String[] args) {
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
}
