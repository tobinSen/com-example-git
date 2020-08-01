package com.example.spring.git;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GitApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitApplication.class, args);
    }



    @Order(1)
//    @Async
    @EventListener(value = {ContextRefreshedEvent.class}) //收影响
    public void event1() {
        System.err.println("event1...");
        throw new RuntimeException("event1...异常了");
    }

    @Order(2)
//    @Async
    @EventListener(value = {ContextRefreshedEvent.class})
    public void event2() {
        System.err.println("event2...");
    }


}
