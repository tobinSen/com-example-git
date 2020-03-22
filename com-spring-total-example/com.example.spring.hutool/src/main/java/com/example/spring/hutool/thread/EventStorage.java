package com.example.spring.hutool.thread;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * 这里分析一下get操纵： 当某个线程得到锁时storage为空，此时它应该wait，下次被唤醒时（任意线程调用notify），
 * storage可能还是空的。因为有可能其他线程清空了storage。如果此时用的是if它将不再判断storage是否为空，直接继续，这样就引起了错误。
 * 但如果用while则每次被唤醒时都会先检查storage是否为空再继续，这样才是正确的操作；生产也是同一个道理。
 */
public class EventStorage {
    private int maxSize;
    private List<Date> storage;

    public EventStorage() {
        maxSize = 10;
        storage = new LinkedList<>();
    }

    public synchronized void set() {
        while (storage.size() == maxSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        storage.add(new Date());
        System.out.printf("Set: %d", storage.size());
        notifyAll();
    }

    public synchronized void get() {
        th:while (storage.size() == 0) { //wait等待被激活的时候，还会进行判断while里的条件十分符合
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                while (storage.size() == 0) {
                    break th; //打标签，跳出多层循环
                }
            }
        }
        System.out.printf("Get: %d: %s", storage.
                size(), ((LinkedList<?>) storage).poll());
        notifyAll();
    }


    static class Producer implements Runnable {
        private EventStorage storage;

        public Producer(EventStorage storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                storage.set();
            }
        }
    }

    static class Consumer implements Runnable {
        private EventStorage storage;

        public Consumer(EventStorage storage) {
            this.storage = storage;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                storage.get();
            }
        }
    }

    public static void main(String[] args) {
        EventStorage storage = new EventStorage();

        Producer producer = new Producer(storage);
        Consumer consumer = new Consumer(storage);

        Thread thread1 = new Thread(producer);
        Thread thread2 = new Thread(consumer);
        thread1.start();
        thread2.start();
    }
}