package com.example.spring.mybatis.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class springListener {

    @Autowired
    private springListener springListener;

    @Bean
    public EventBus eventBus() {
        EventBus eventBus = new EventBus();
        eventBus.register(springListener);
        return eventBus;
    }

    @Subscribe
    public void listener(String event) {
        System.out.println("receive msg:" + event);
    }
}
