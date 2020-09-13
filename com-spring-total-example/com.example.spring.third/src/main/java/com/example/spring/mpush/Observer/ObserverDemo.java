package com.example.spring.mpush.Observer;

import java.util.Observable;

public class ObserverDemo extends Observable {

    public static void main(String[] args) {
        ObserverDemo observerDemo = new ObserverDemo();
        // 添加观察者
        observerDemo.addObserver((o, arg) -> {
            System.out.println(arg);
        });

        observerDemo.addObserver((o, arg) -> {
            System.out.println(o);
        });

        observerDemo.setChanged(); //发生变化
        observerDemo.notifyObservers(); //通知

    }
}
