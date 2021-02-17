package com.example.spring.weixin.demo;

public class MainDemo {


    interface A {
        default void get() {
            System.out.println("A.get()");
        }
    }

    class B {
        void get() {
            System.out.println("B.get()");
        }

    }


    public static void main(String[] args) throws InterruptedException {
        System.out.println("main():"+ Thread.currentThread().getName());

        Thread t = new Thread(() -> {
            System.out.println("t 的线程");
            try {
                Thread.currentThread().join(); //当前线程一直阻塞
                System.out.println("Thread.currentThread():"+ Thread.currentThread().getName());
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t.start();
        System.out.println("t的线程ID：" + t.getName());
//        t.join(); // t线程完成后，才加入其它线程


        System.out.println("Thread.currentThread().getID:"+ Thread.currentThread().getName());

        System.out.println("线程结束了。。。。");
    }

}
