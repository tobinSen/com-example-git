package com.example.spring.webflux.test;

import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;
import java.util.Random;

public class MonoTest {

    public static void main(String[] args) {
        //Mono测试(前发布，后订阅)
        Mono.just(new Random().nextInt()).subscribe(System.out::println);
        Mono.justOrEmpty(Optional.empty()).subscribe(System.out::println);
        Mono.justOrEmpty(new Date()).subscribe(System.out::println);

        Mono.create(sink -> {
            sink.success(new Random().nextInt(2));
            System.out.println(sink.currentContext().toString());
        }).subscribe(System.out::println);

        Mono.delay(Duration.ofSeconds(3, 1)).subscribe(System.out::println);

        //通过fromRunnable创建，并实现异常处理
        Mono.fromRunnable(() -> {
            System.out.println("thread run");
            throw new RuntimeException("thread run error");
        }).subscribe(System.out::println, System.err::println);

        //通过fromCallable创建
        Mono.fromCallable(() -> "callable run ").subscribe(System.out::println);
        //通过fromSupplier创建
        Mono.fromSupplier(() -> "create from supplier").subscribe(System.out::println);

    }
}
