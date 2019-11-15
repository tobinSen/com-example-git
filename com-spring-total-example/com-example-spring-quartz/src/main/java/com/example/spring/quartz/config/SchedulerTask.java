package com.example.spring.quartz.config;

import org.springframework.stereotype.Component;

@Component // 此注解必加
public class SchedulerTask {

    public void start(String name) {
        System.out.println("start:" + System.currentTimeMillis() + ":" + name);
    }

    public void end(String name) {
        System.out.println("end:" + System.currentTimeMillis() + ":" + name);
    }
}
