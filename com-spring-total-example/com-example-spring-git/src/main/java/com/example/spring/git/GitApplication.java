package com.example.spring.git;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 场景：代码commit到本地仓库，还没有push到远程仓库，这时要回退代码。
 *
 * 介绍下Reset Head中三种Reset Type类型：
 *
 * 1.Mixed（默认）：它回退到某个版本，本地会保留源码，回退commit和index信息(add 信息)，若要提交重新commit。
 *
 * 2.soft: 回退到某个版本，只回退了commit的信息，不会恢复到index file一级，若要提交重新commit。
 *
 * 3.Hard:彻底回退到某个版本，本地的源码也会变为上一个版本的内容。
 */
@SpringBootApplication
@EnableAsync
public class GitApplication {

    public static void main(String[] args) {
        SpringApplication.run(GitApplication.class, args);
    }

//
//    @Order(1)
//    @Async
//    @EventListener(value = {ContextRefreshedEvent.class}) //收影响
//    public void event1() {
//        System.err.println("event1...");
//        throw new RuntimeException("event1...异常了");
//    }
//
//    @Order(2)
//    @Async
//    @EventListener(value = {ContextRefreshedEvent.class})
//    public void event2() {
//        System.err.println("event2...");
//    }


}
