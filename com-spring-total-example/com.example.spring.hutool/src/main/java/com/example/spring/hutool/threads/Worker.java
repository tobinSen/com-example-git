package com.example.spring.hutool.threads;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

public class Worker implements Runnable {

    // 任务队列，用于每个子任务
    private Queue<Map<String, Long>> workQueue;

    // 计数器
    private CountDownLatch countDownLatch;

    public Worker(Queue<Map<String, Long>> workQueue, CountDownLatch countDownLatch) {
        this.workQueue = workQueue;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {

    }
}
