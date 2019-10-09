package com.example.spring.jedis.test;

import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.stream.IntStream;

public class BoomFilterTest {

    //传入期望处理的元素数量，及最期望的误报的概率
    private static BloomFilter filter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), 500, 0.01);

    public static void main(String[] args) {
        IntStream.range(0, 100).forEach(filter::put);
        boolean b = filter.mightContain(-1);
        System.out.println(b);
    }
}
