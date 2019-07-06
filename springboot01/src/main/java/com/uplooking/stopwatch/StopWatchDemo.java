package com.uplooking.stopwatch;

import com.google.common.base.Stopwatch;

import java.util.concurrent.TimeUnit;

public class StopWatchDemo {

    public static void main(String[] args) throws Exception {
        // 创建stopwatch并开始计时
        Stopwatch stopwatch = Stopwatch.createStarted();
        Thread.sleep(1980);
        // 以秒打印从计时开始至现在的所用时间,向下取整(运行时经过的时间)
        System.out.println(stopwatch.elapsed(TimeUnit.SECONDS)); // 1
        // 停止计时
        stopwatch.stop();
        System.out.println(stopwatch.elapsed(TimeUnit.SECONDS)); // 1
        // 再次计时
        stopwatch.start();
        Thread.sleep(100);
        System.out.println(stopwatch.elapsed(TimeUnit.SECONDS)); // 2
        // 重置并开始
        stopwatch.reset().start();
        Thread.sleep(1030);
        // 检查是否运行
        System.out.println(stopwatch.isRunning()); // true
        long millis = stopwatch.elapsed(TimeUnit.MILLISECONDS); // 1031
        System.out.println(millis);
        // 打印
        System.out.println(stopwatch.toString()); // 1.03 s

    }

}
