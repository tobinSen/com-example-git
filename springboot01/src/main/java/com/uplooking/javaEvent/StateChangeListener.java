package com.uplooking.javaEvent;

import java.util.EventListener;

/**
 * java的事件监听EventListener
 */
public class StateChangeListener implements EventListener {

    public void handleEvent(MyEvent event) {
        System.out.println("触发状态改变事件。。。");
        System.out.println("当前事件源状态为：" + event.getSourceState());
        System.out.println("。。。。。。。。。。。。。。。。。。。。。。。");
    }
}
