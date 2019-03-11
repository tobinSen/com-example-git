package com.uplooking.delay;

import lombok.Data;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@Data
public class Member implements Delayed {

    private String name;
    private Long expire;
    private Long delay;

    public Member(String name, Long delay, TimeUnit timeUnit) {
        this.name = name;
        //当前时间上添加延迟时间
        this.expire = System.currentTimeMillis() + this.delay;
        //保存延迟的时间（1分钟）
        this.delay = TimeUnit.MILLISECONDS.convert(delay, timeUnit);
    }

    @Override //计算延迟时间是否到达
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.expire - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override//决定了你优先级队列的弹出操作（1分钟-相差的时间= 剩余到达时间区间）
    public int compareTo(Delayed o) {
        return (int) (this.delay - this.getDelay(TimeUnit.MILLISECONDS));
    }
}
