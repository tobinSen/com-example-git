package com.example.spring.mpush.Observer;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.Observable;

public class ObserverDemo extends Observable {

    public static void main01(String[] args) {
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

    public static void main(String[] args) {
        List<String> list = Lists.newArrayList("1", "2", "3");
        System.out.println(JSON.toJSON(list));
    }
}
