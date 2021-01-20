package com.example.spring.mybatis;

import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;


@Component
public class MySmartLifecycle implements SmartLifecycle {


    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {

    }

    @Override
    public void start() {
        System.err.println("================== SmartLifecycle start ====================");
    }

    @Override
    public void stop() {

        System.err.println("================== SmartLifecycle stop ====================");


    }

    @Override
    public boolean isRunning() {
        return false;
    }

    @Override
    public int getPhase() {
        return 0;
    }
}
