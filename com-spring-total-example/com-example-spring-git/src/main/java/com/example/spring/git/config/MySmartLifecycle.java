package com.example.spring.git.config;

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

    /**
     * 只有isAutoStartup = true & isRunning=false 的时候才会触发这个接口
     */
    @Override
    public void start() {
        System.err.println("================== SmartLifecycle start ====================");
    }

    /**
     * isRunning = true的时候会触发这个接口
     */
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
