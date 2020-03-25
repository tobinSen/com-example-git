package com.example.spring.hutool.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationListener {

    @EventListener
    public void listener(MyApplicationEvent event) {
        System.out.println(event.uid);
    }
}
