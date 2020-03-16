package com.example.spring.mongodb;

import org.springframework.context.ApplicationEvent;

public class MyEvent extends ApplicationEvent {

    private String eid;

    public MyEvent(Object source, String eid) {
        super(source);
        this.eid = eid;
    }

    public String getEid() {
        return eid;
    }
}
