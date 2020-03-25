package com.example.spring.hutool.listener;

import org.springframework.context.ApplicationEvent;

public class MyApplicationEvent extends ApplicationEvent {

    public String uid;

    public MyApplicationEvent(Object source, String uid) {
        super(source);
        this.uid = uid;
    }
}
