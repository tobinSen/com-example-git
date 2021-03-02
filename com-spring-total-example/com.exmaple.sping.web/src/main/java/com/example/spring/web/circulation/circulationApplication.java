package com.example.spring.web.circulation;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ClassPathXmlApplicationContext
 * AnnotationConfigApplicationContext
 */
public class circulationApplication {

    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:application.xml");
//        context.refresh();
//        context.start();

        ManagerA managerA = (ManagerA)context.getBean("managerA");
        Thread.currentThread().join();


        //  new Timer() 需要等待上次任务执行完后才会进行下个任务的运行
        //  scheduleAtFixedRate 上一个任务还未执行完到了下个任务的执行时间会立刻执行
        // scheduleWithFixedDelay 等待上一个任务执行完后，在等待时间间隔后在进行执行
//        new Timer().scheduleAtFixedRate(new TimerTask() {
//                                            @Override
//                                            public void run() {
//                                                System.out.println("任务开始 time: " + LocalDateTime.now());
//                                                try {
//                                                    Thread.sleep(5000);
//                                                } catch (InterruptedException e) {
//                                                    e.printStackTrace();
//                                                }
//                                                System.out.println("任务结束 time: " + LocalDateTime.now());
//
//                                            }
//                                        }, 2000, 3000);

            ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(2);
            scheduledThreadPool.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    System.out.println("任务开始 time: " + LocalDateTime.now());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("任务结束 time: " + LocalDateTime.now());
                }
            }, 2000, 3000, TimeUnit.MILLISECONDS);
    }
}
