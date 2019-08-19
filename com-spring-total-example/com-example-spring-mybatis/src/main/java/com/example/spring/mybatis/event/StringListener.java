package com.example.spring.mybatis.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

public class StringListener {

    private static EventBus eventBus = new EventBus();

    @Subscribe
    public static void listener(String event) throws Exception {
        System.out.println(event);
    }

    public static void main(String[] args) {
        eventBus.register(new StringListener());
        eventBus.post("event1...start");
    }
}
