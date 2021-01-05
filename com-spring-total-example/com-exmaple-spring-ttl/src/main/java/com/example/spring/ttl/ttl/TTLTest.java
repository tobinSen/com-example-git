package com.example.spring.ttl.ttl;

import com.alibaba.ttl.threadpool.TtlExecutors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TTLTest {

    //需要注意的是，使用TTL的时候，要想传递的值不出问题，线程池必须得用TTL加一层代理（下面会讲这样做的目的）
    //todo 这里需要使用ttl自带的包装形式
    private static ExecutorService executorService = TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(2));
    //private statics ExecutorService executorService = Executors.newFixedThreadPool(2);

    //private static ThreadLocal tl = new TransmittableThreadLocal<>(); //这里采用TTL的实现
    private static ThreadLocal tl = new ThreadLocal<>(); //这里采用TTL的实现

    //线程-->嵌套线程池创建线程（本质上还是说是线程嵌套，只是说创建的方式是不一样的）
    public static void main(String[] args) {
        /**
         * TransmittableThreadLocal:
         *  实现步骤：
         *      1.将线程池进行包装
         *      2.将值添加到TransmittableThreadLocal
         *
         *  1.子父线程嵌套传值（单独使用）：原理：InheritableThreadLocal
         *  2.父线程嵌套线程池的问题：
         *       1.开始的时候子线程可以获取值，但是当这个值被改变的时候每个创建的本地线程是不会改变值的（这时候的线程是独立的）
         *       2.通过TtlExecutors.getTtlExecutorService(Executors.newFixedThreadPool(2))方式进行包装，让其产生的线程是包装了的
         *  3.其实没有解决兄弟线程之间进行传值问题
         */

        new Thread(() -> {

            String mainThreadName = "main_01";

            //在父线程中注入值
            tl.set(1);

            //在子线程中获取值

            executorService.execute(() -> {
                sleep(1L);
                System.out.println(String.format("本地变量改变之前(1), 父线程名称-%s, 子线程名称-%s, 变量值=%s", mainThreadName, Thread.currentThread().getName(), tl.get()));
                tl.set(2);
                executorService.execute(() -> {
                    sleep(1L);
                    System.out.println(String.format("本地变量改变之前(1), 父线程名称-%s, 子线程名称-%s, 变量值=%s", mainThreadName, Thread.currentThread().getName(), tl.get()));
                    tl.set(2);
                });
            });

//            executorService.execute(() -> {
//                sleep(1L);
//                System.out.println(String.format("本地变量改变之前(1), 父线程名称-%s, 子线程名称-%s, 变量值=%s", mainThreadName, Thread.currentThread().getName(), tl.get()));
//            });

            sleep(1L); //确保上面的会在tl.set执行之前执行
            //tl.set(2); //等上面的线程池第一次启用完了，父线程再给自己赋值
//
//            executorService.execute(() -> {
//                sleep(1L);
//                System.out.println(String.format("本地变量改变之后(2), 父线程名称-%s, 子线程名称-%s, 变量值=%s", mainThreadName, Thread.currentThread().getName(), tl.get()));
//            });

//            executorService.execute(() -> {
//                sleep(1L);
//                System.out.println(String.format("本地变量改变之后(2), 父线程名称-%s, 子线程名称-%s, 变量值=%s", mainThreadName, Thread.currentThread().getName(), tl.get()));
//            });
//
//            executorService.execute(() -> {
//                sleep(1L);
//                System.out.println(String.format("本地变量改变之后(2), 父线程名称-%s, 子线程名称-%s, 变量值=%s", mainThreadName, Thread.currentThread().getName(), tl.get()));
//            });

            System.out.println(String.format("线程名称-%s, 变量值=%s", Thread.currentThread().getName(), tl.get()));

//            /**
//             * 线程嵌套线程之间进行传值
//             */
//            new Thread(() -> {
//                sleep(1L);
//                System.out.println(String.format("Thread1本地变量改变之后(2), 父线程名称-%s, 子线程名称-%s, 变量值=%s", mainThreadName, Thread.currentThread().getName(), tl.get()));
//            }).start();
//
//            sleep(1L);
//            tl.set(2L);
//
//            new Thread(() -> {
//                sleep(1L);
//                System.out.println(String.format("Thread2本地变量改变之后(2), 父线程名称-%s, 子线程名称-%s, 变量值=%s", mainThreadName, Thread.currentThread().getName(), tl.get()));
//            }).start();
//
//        }).start();


//        new Thread(() -> {
//
//            String mainThreadName = "main_02";
//
//            tl.set(3);
//
//            executorService.execute(() -> {
//                sleep(1L);
//                System.out.println(String.format("本地变量改变之前(3), 父线程名称-%s, 子线程名称-%s, 变量值=%s", mainThreadName, Thread.currentThread().getName(), tl.get()));
//            });
//
//            executorService.execute(() -> {
//                sleep(1L);
//                System.out.println(String.format("本地变量改变之前(3), 父线程名称-%s, 子线程名称-%s, 变量值=%s", mainThreadName, Thread.currentThread().getName(), tl.get()));
//            });
//
//            executorService.execute(() -> {
//                sleep(1L);
//                System.out.println(String.format("本地变量改变之前(3), 父线程名称-%s, 子线程名称-%s, 变量值=%s", mainThreadName, Thread.currentThread().getName(), tl.get()));
//            });
//
//            sleep(1L); //确保上面的会在tl.set执行之前执行
//            tl.set(4); // 等上面的线程池第一次启用完了，父线程再给自己赋值
//
//            executorService.execute(() -> {
//                sleep(1L);
//                System.out.println(String.format("本地变量改变之后(4), 父线程名称-%s, 子线程名称-%s, 变量值=%s", mainThreadName, Thread.currentThread().getName(), tl.get()));
//            });
//
//            executorService.execute(() -> {
//                sleep(1L);
//                System.out.println(String.format("本地变量改变之后(4), 父线程名称-%s, 子线程名称-%s, 变量值=%s", mainThreadName, Thread.currentThread().getName(), tl.get()));
//            });
//
//            executorService.execute(() -> {
//                sleep(1L);
//                System.out.println(String.format("本地变量改变之后(4), 父线程名称-%s, 子线程名称-%s, 变量值=%s", mainThreadName, Thread.currentThread().getName(), tl.get()));
//            });
//
//            System.out.println(String.format("线程名称-%s, 变量值=%s", Thread.currentThread().getName(), tl.get()));
//
        }).start();

    }

    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
