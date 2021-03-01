package com.example.spring.web.circulation;

import org.springframework.context.support.ClassPathXmlApplicationContext;

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
    }
}
