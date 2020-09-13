package com.example.spring.layui.reflect;

import com.google.common.util.concurrent.MoreExecutors;

import java.lang.reflect.Field;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Cat extends Animal {

    private String catName;
    protected Integer catAge;
    String catAddress;
    public String catBirthday;


    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public Integer getCatAge() {
        return catAge;
    }

    public void setCatAge(Integer catAge) {
        this.catAge = catAge;
    }

    public String getCatAddress() {
        return catAddress;
    }

    public void setCatAddress(String catAddress) {
        this.catAddress = catAddress;
    }

    public String getCatBirthday() {
        return catBirthday;
    }

    public void setCatBirthday(String catBirthday) {
        this.catBirthday = catBirthday;
    }

    public static void main(String[] args) {
        //让当前线程直接去执行这个任务，执行完后在返回
        MoreExecutors.directExecutor().execute(() -> {
            System.out.println(Thread.currentThread().getName());
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(Thread.currentThread().getName());

    }

    public static void main02(String[] args) {
        Cat a1 = new Cat() {

            //通过匿名内部类的方式进行重写
            @Override
            public void setName(String name) {
                super.setName(name);
            }
        };
        Cat a2 = new Cat() {{
            //构造代码块
            setName("构造代码块调用父类的方法");
        }};

        /**
         * 线程池的大小：
         *  cpu数量 * cpu使用系数(0-1之间，取0.9) * (1+等待时间/计算时间)
         */
        new ThreadPoolExecutor(1, 1, 1, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10)) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                //这里可以记录线程执行前的信息
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                //这里可以记录线程执行后的信息
            }
        };

    }

    public static void main01(String[] args) {
        //只能获取本类的
        Field[] declaredFields = Cat.class.getDeclaredFields();
        //只能获取本类public 和父类public
        Field[] fields = Cat.class.getFields();
        for (Field field : declaredFields) {
            System.out.println(field.getName());
        }
        System.out.println("================");
        for (Field field : fields) {
            System.out.println(field.getName());
        }

        //catName
        //catAge
        //catAddress
        //catBirthday
        //================
        //catBirthday
        //birthday
    }
}
