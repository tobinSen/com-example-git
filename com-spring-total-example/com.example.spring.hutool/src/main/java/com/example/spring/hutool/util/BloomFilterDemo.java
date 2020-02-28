package com.example.spring.hutool.util;

import cn.hutool.bloomfilter.BitMapBloomFilter;

public class BloomFilterDemo {

    public static void main(String[] args) {
        // 初始化
        BitMapBloomFilter filter = new BitMapBloomFilter(10);
        filter.add("123");
        filter.add("abc");
        filter.add("ddd");

// 查找
        System.out.println(filter.contains("abc"));

    }
}
