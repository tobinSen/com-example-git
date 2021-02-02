package com.example.spring.hutool.masterworker;

import java.util.concurrent.TimeUnit;

public class MyWorker1 extends Worker {

    @Override
    protected Object handle(Task input) {
        // 具体的逻辑

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 123;
    }
}
