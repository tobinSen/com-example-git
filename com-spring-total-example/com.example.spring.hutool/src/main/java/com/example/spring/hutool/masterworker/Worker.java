package com.example.spring.hutool.masterworker;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class Worker implements Runnable {
    private ConcurrentLinkedQueue<Task> workQueue;

    private ConcurrentHashMap<String, Object> resultMap;

    public void setWorkerQueue(ConcurrentLinkedQueue<Task> workQueue) {
        this.workQueue = workQueue;
    }

    public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    @Override
    public void run() {
        //处理一个个任务
        while (true) {
            //从队列中取出一个元素
            Task input = this.workQueue.poll();
            if (null == input) break;
            //真正的去做业务处理
            Object outPut = handle(input);
            //存放任务的结果
            this.resultMap.put(String.valueOf(input.getId()), outPut);
        }
    }
    //单独抽出来 给子类重写，更加灵活

    protected abstract Object handle(Task input);

}
