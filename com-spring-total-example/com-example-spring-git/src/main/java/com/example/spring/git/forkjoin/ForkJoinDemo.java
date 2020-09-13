package com.example.spring.git.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;


/**
 * TODO oss中，2020/08/30/sngsxafd01.jpg  日期会自动进行传进目录objectName
 */
public class ForkJoinDemo {

    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();

        ForkJoinTask<Long> task = new ForkJoinSumCalculate(0, 10000L);
        Long sum = pool.invoke(task);
        System.out.println(sum);

        long longSum = LongStream.rangeClosed(0, 1000000).parallel().reduce(Long::sum).orElse(0L);
        System.out.println(longSum);

    }
}

class ForkJoinSumCalculate extends RecursiveTask<Long> {

    private long start;
    private long end;
    private static final long THURSHOLD = 1000L; //临界值

    public ForkJoinSumCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    //返回拆分后的总计
    @Override
    protected Long compute() {

        if (end - start <= THURSHOLD) { //不用拆的情况
            long sum = 0L;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else { //进行拆分的情况
            long middle = (start + end) / 2;
            ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle);
            left.fork();
            ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle + 1, end);
            right.fork();
            return left.join() + right.join();
        }
    }
}
