package com.example.spring.mybatis.completableFuture;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class CompletableFutureTest {

    static ExecutorService executor = Executors.newFixedThreadPool(3, new ThreadFactory() {
        int count = 1;

        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable, "custom-executor-" + count++);
        }
    });

    static Random random = new Random();

//    public statics void main(String[] args) throws Exception {
//        try {
////            completedFutureExample();
////            completeExceptionallyExample();
////            thenApplyExample();
//            thenApplyAsyncWithExecutorExample();
//        } finally {
//            executor.shutdown();
//        }
//    }

    static void completedFutureExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message"); //completedValue,执行完线程后返回的值
        //assertTrue(cf.isDone());
        //assertEquals("message", cf.getNow(null));
        System.out.println(cf.isDone() + "--" + cf.getNow(null));
    }

    static void completeExceptionallyExample() throws Exception {
        CompletableFuture<String> cf =
                CompletableFuture.completedFuture("message")
                        //当需要获取到result信息时候，不能使用异步方式
                        .thenApply(String::toUpperCase);//线程串行化 //始终是返回一个全新的CompletableFuture导致里面没有存储result
        System.out.println(cf.getNow(null));
        //System.out.println(cf.join());

        //java9   CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS)
        //.thenApplyAsync(String::toUpperCase, CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS));

        //handle 可以处理异常情况
        CompletableFuture<String> exceptionHandler = cf.handle((s, th) -> {
            System.out.println(s);
            try {
                if (s.equals("MESSAGE")) throw new RuntimeException("runtimeException");
                return null;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                cf.completeExceptionally(new RuntimeException("completed exceptionally")); //get()方法共用
                return "";
            }
            //return (th != null) ? "message upon cancel" : "";
        }).exceptionally(e -> {
            System.out.println(e.getMessage());
            return null;
        });//有异常才会执行
        System.out.println(exceptionHandler.get());


        //assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
//        try {
//            cf.join();
//            //fail("Should have thrown an exception");
//        } catch (CompletionException ex) { // just for testing
//            assertEquals("completed exceptionally", ex.getCause().getMessage());
//        }
//
//        assertEquals("message upon cancel", exceptionHandler.join());
    }

    static void runAsyncExample() {
        CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> {
            assertTrue(Thread.currentThread().isDaemon());
            randomSleep();
        });
        assertFalse(cf.isDone());
        sleepEnough();
        assertTrue(cf.isDone());
    }

    static void thenApplyExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApply(s -> {
            assertFalse(Thread.currentThread().isDaemon());
            return s.toUpperCase();
        });
        assertEquals("MESSAGE", cf.getNow(null));
        System.out.println(cf.getNow(null));
    }

    static void thenApplyAsyncExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            assertTrue(Thread.currentThread().isDaemon());
            randomSleep();
            return s.toUpperCase();
        });
        //assertNull(cf.getNow(null));
        assertEquals("MESSAGE", cf.join());
    }

    static void thenApplyAsyncWithExecutorExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message").thenApplyAsync(s -> {
            assertTrue(Thread.currentThread().getName().startsWith("custom-executor-"));
            assertFalse(Thread.currentThread().isDaemon());
            randomSleep();
            return s.toUpperCase();
        }, executor);

        //assertNull(cf.getNow(null));
        assertEquals("MESSAGE", cf.join());
        System.out.println(cf.join());
    }

    static void thenAcceptExample() {
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture("thenAccept message")
                .thenAccept(s -> result.append(s));
        assertTrue("Result was empty", result.length() > 0);
    }

    static void thenAcceptAsyncExample() {
        StringBuilder result = new StringBuilder();
        CompletableFuture<Void> cf = CompletableFuture.completedFuture("thenAcceptAsync message")
                .thenAcceptAsync(s -> result.append(s));
        cf.join();
        assertTrue("Result was empty", result.length() > 0);
    }

    static void cancelExample() {
        CompletableFuture<String> cf = CompletableFuture.completedFuture("message")
                .thenApplyAsync(String::toUpperCase, executor);

        CompletableFuture<String> cf2 = cf.exceptionally(throwable -> "canceled message");
        assertTrue("Was not canceled", cf.cancel(true));
        assertTrue("Was not completed exceptionally", cf.isCompletedExceptionally());
        assertEquals("canceled message", cf2.join());
    }

    static void applyToEitherExample() {
        String original = "Message";
        CompletableFuture<String> cf1 = CompletableFuture.completedFuture(original)
                .thenApplyAsync(s -> delayedUpperCase(s));
        CompletableFuture<String> cf2 = cf1.applyToEither(
                CompletableFuture.completedFuture(original).thenApplyAsync(s -> delayedLowerCase(s)),
                s -> s + " from applyToEither");
        assertTrue(cf2.join().endsWith(" from applyToEither"));
    }

    static void acceptEitherExample() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture<Void> cf = CompletableFuture.completedFuture(original)
                .thenApplyAsync(s -> delayedUpperCase(s))
                .acceptEither(CompletableFuture.completedFuture(original).thenApplyAsync(s -> delayedLowerCase(s)),
                        s -> result.append(s).append("acceptEither"));
        cf.join();
        assertTrue("Result was empty", result.toString().endsWith("acceptEither"));
    }

    static void runAfterBothExample() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture(original).thenApply(String::toUpperCase).runAfterBoth(
                CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                () -> result.append("done"));
        assertTrue("Result was empty", result.length() > 0);
    }

    static void thenAcceptBothExample() {
        String original = "Message";
        StringBuilder result = new StringBuilder();
        CompletableFuture.completedFuture(original).thenApply(String::toUpperCase).thenAcceptBoth(
                CompletableFuture.completedFuture(original).thenApply(String::toLowerCase),
                (s1, s2) -> result.append(s1 + s2));
        assertEquals("MESSAGEmessage", result.toString());
    }

    static void thenCombineExample() {
        String original = "Message";
        CompletableFuture<String> cf = CompletableFuture.completedFuture(original).thenApply(s -> delayedUpperCase(s))
                .thenCombine(CompletableFuture.completedFuture(original).thenApply(s -> delayedLowerCase(s)),
                        (s1, s2) -> s1 + s2);
        assertEquals("MESSAGEmessage", cf.getNow(null));
    }

    static void thenCombineAsyncExample() {
        String original = "Message";
        CompletableFuture<String> cf = CompletableFuture.completedFuture(original)
                .thenApplyAsync(s -> delayedUpperCase(s))
                .thenCombine(CompletableFuture.completedFuture(original).thenApplyAsync(s -> delayedLowerCase(s)),
                        (s1, s2) -> s1 + s2);
        assertEquals("MESSAGEmessage", cf.join());
    }

    static void thenComposeExample() {
        String original = "Message";
        CompletableFuture<String> cf = CompletableFuture.completedFuture(original).thenApply(s -> delayedUpperCase(s))
                .thenCompose(upper -> CompletableFuture.completedFuture(original).thenApply(s -> delayedLowerCase(s))
                        .thenApply(s -> upper + s));
        assertEquals("MESSAGEmessage", cf.join());
    }

    static void anyOfExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> delayedUpperCase(s)))
                .collect(Collectors.toList());
        CompletableFuture.anyOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((res, th) -> {
            if (th == null) {
                assertTrue(isUpperCase((String) res));
                result.append(res);
            }
        });
        assertTrue("Result was empty", result.length() > 0);
    }

    static void allOfExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApply(s -> delayedUpperCase(s)))
                .collect(Collectors.toList());
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()])).whenComplete((v, th) -> {
            futures.forEach(cf -> assertTrue(isUpperCase(cf.getNow(null))));
            result.append("done");
        });
        assertTrue("Result was empty", result.length() > 0);
    }

    static void allOfAsyncExample() {
        StringBuilder result = new StringBuilder();
        List<String> messages = Arrays.asList("a", "b", "c");
        List<CompletableFuture<String>> futures = messages.stream()
                .map(msg -> CompletableFuture.completedFuture(msg).thenApplyAsync(s -> delayedUpperCase(s)))
                .collect(Collectors.toList());
        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]))
                .whenComplete((v, th) -> {
                    futures.forEach(cf -> assertTrue(isUpperCase(cf.getNow(null))));
                    result.append("done");
                });
        allOf.join();
        assertTrue("Result was empty", result.length() > 0);
    }

    private static boolean isUpperCase(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLowerCase(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static String delayedUpperCase(String s) {
        randomSleep();
        return s.toUpperCase();
    }

    private static String delayedLowerCase(String s) {
        randomSleep();
        return s.toLowerCase();
    }

    private static void randomSleep() {
        try {
            Thread.sleep(random.nextInt(1000));
        } catch (InterruptedException e) {
            // ...
        }
    }

    private static void sleepEnough() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // ...
        }
    }

    public static void main(String[] args) throws Exception {
        method();
    }

    private static void method() throws Exception {
//        CompletableFuture<String> future = CompletableFuture.completedFuture("completableFuture");
//        future.whenComplete((s, throwable) -> System.out.println(s));

//        System.out.println(future.get());
//        System.out.println(future.join());
        CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
            //System.out.println("CompletableFuture-->whenComplete");
            return "CompletableFuture-->whenComplete";
        }).whenComplete((s, throwable) -> System.out.println(s))
                .whenCompleteAsync((s, throwable) -> System.out.println(s))
                .whenCompleteAsync((s, throwable) -> System.out.println(s), executor)

                .thenApply(s -> {
                    System.out.println(s);
                    return s;
                })
                .thenApplyAsync(s -> {
                    System.out.println(s);
                    return s;
                })
                .thenApplyAsync(s -> {
                    System.out.println(s);
                    return s;
                }, executor)

                .handle((s, throwable) -> {
                    System.out.println(s);
                    return s;
                })
                .handleAsync((s, throwable) -> {
                    System.out.println(s);
                    return s;
                })
                .handleAsync((s, throwable) -> {
                    System.out.println(s);
                    return s;
                }, executor)

                .thenAccept(System.out::println)
                .thenAcceptAsync(System.out::println)
                .thenAcceptAsync(System.out::println, executor)

                .thenRun(() -> System.out.println("thenRun"))
                .thenRunAsync(() -> System.out.println("thenRun"))
                .thenRunAsync(() -> System.out.println("thenRun"), executor);

        CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
            return "abc";
        });
        CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
            return "100";
        });

        //将2个future的结果进行返回
        CompletableFuture<String> f1 = future1.thenCombine(future2, (x, y) -> y + "-" + x);
        CompletableFuture<String> f2 = future1.thenCombineAsync(future1, (x, y) -> y + "-" + x);
        CompletableFuture<String> f3 = future1.thenCombineAsync(future1, (x, y) -> y + "-" + x, executor);
        System.out.println(f1.get()); //abc-100
        System.out.println(f2.get()); //abc-100
        System.out.println(f3.get()); //abc-100

        //纯消费
        future1.thenAcceptBoth(f2, (x, y) -> System.out.println(x + "---" + y));
        future1.thenAcceptBothAsync(f2, (x, y) -> System.out.println(x + "---" + y));
        future1.thenAcceptBothAsync(f2, (x, y) -> System.out.println(x + "---" + y), executor);

        //2个future的类型必须一致（谁先执行完，就执行谁的返回结果后消费）
        future1.acceptEither(future2, System.out::println);
        future1.acceptEitherAsync(future2, System.out::println);
        future1.acceptEitherAsync(future2, System.out::println, executor);

        //谁先执行完，就是执行function，并返回值
        CompletableFuture<String> toEither = future1.applyToEither(future2, s -> s);
        CompletableFuture<String> eitherAsync = future1.applyToEitherAsync(future2, s -> s);
        CompletableFuture<String> toEitherAsync = future1.applyToEitherAsync(future2, s -> s, executor);
        System.out.println(toEither.get());
        System.out.println(eitherAsync.get());
        System.out.println(toEitherAsync.get());

        //第一个操作完成时，将其结果作为参数传递给第二个操作,返回一个新的CompletableFuture（串行操作）
        future1.thenCompose(x -> CompletableFuture.supplyAsync(() -> (x + 10) + ""));
        future1.thenComposeAsync(x -> CompletableFuture.supplyAsync(() -> (x + 10) + ""));
        future1.thenComposeAsync(x -> CompletableFuture.supplyAsync(() -> (x + 10) + ""), executor);

        CompletableFuture.allOf(future1, future2);
        Void join1 = CompletableFuture.allOf(future1, future2).join();   //null
        Object join = CompletableFuture.anyOf(future1, future2).join(); //100

        //future2.whenComplete((x, throwable) -> System.out.println("allOf:"+x));

    }
}
