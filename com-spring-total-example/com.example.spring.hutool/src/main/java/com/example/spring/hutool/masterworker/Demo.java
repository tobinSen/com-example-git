package com.example.spring.hutool.masterworker;

public class Demo {
    public static void main(String[] args) {
        Master master = new Master(new MyWorker1(), 3);
        master.submit(new Task());
    }

}
