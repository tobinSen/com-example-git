package com.uplooking.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyApplicationListener implements ApplicationListener<ApplicationEvent> {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 步骤：
     * 1.写一个监听器来监听某个事件(ApplicationEvent及子类)
     * 2.把监听器加入到容器
     * 3.只要容器中有相关事件的发布，我们就能监听到这个事件
     * 4.发布一个事件
     */

    public void publish() {
        applicationContext.publishEvent(new ApplicationEvent("事件发布测试") {
        });
    }

    @EventListener(classes = {ApplicationEvent.class})
    public void listen(ApplicationEvent event) {
        System.out.println("监听的事件" + event);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("收到的事件" + event);
    }
}
