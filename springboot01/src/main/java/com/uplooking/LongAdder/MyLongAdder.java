package com.uplooking.LongAdder;

import java.util.concurrent.atomic.LongAdder;

public class MyLongAdder {
    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        longAdder.add(199);
        System.out.println(longAdder.sum());
    }
}
