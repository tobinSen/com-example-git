package com.example.spring.git.config;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ServiceC {

    public static void main(String[] args) throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        list.stream().map(x -> x + 1).forEach(System.out::print);
        list.stream().flatMap(Stream::of).forEach(System.out::print);

        Thread.currentThread().join();
    }
}
