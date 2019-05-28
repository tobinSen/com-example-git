package com.uplooking.CompletableFuture;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@Log4j2
public class MyCompletableFuture {

    private Executor executor = Executors.newFixedThreadPool(10);

//    public void test() {
//        //创建一个异步的操作，操作完后进行消费，和记录是否含有报错信息
//        CompletableFuture<Void> voidCompletableFuture = CompletableFuture.supplyAsync(() -> {
//            System.out.println("result:" + 10);
//            return 10;
//        }, executor).thenAccept(x -> x.intValue()).whenComplete((o, e) -> log.error(e.getMessage()));
//
//        Void join = CompletableFuture.allOf(voidCompletableFuture).join();
//    }

    public static void main(String[] args) throws Exception{
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            int i = 1 / 0;
            return 100;
        });
        future.join();
        //future.get();
    }

}
