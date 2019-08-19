package com.example.spring.mybatis.config;

import com.google.common.util.concurrent.*;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Future {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(10));
        //任务1
        ListenableFuture<Integer> future1 = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("call future 1.");
                return 111;
            }
        });

        //任务2
        ListenableFuture future2 = service.submit(new Callable<Integer>() {
            public Integer call() throws InterruptedException {
                Thread.sleep(1000);
                System.out.println("call future 2.");
                //       throw new RuntimeException("----call future 2.");
                return 2;
            }
        });

        //注册一个回调
        Futures.addCallback(future1, new FutureCallback<Integer>() {
            @Override
            public void onSuccess(@Nullable Integer result) {
                System.out.println("onSuccess: " + result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.getMessage());

            }
        }, service);

        ListenableFuture allFutures = Futures.allAsList(future1, future2);

        System.out.println("allFutures" + allFutures.get());

        ListenableFuture transform = Futures.transform(allFutures, (list) -> {
            System.out.println(list.toString());
            return list;
        }, service);

        System.out.println("transform: " + transform.get());

        //全部执行完后回调
        Futures.whenAllComplete(future1, future2).call(new Callable() {
            @Override
            public Object call() throws Exception {
                System.out.println("future1 ...future2 on success");
                return null;
            }
        }, service);

        service.shutdown();
    }

}
