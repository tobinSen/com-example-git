package com.example.spring.mybatis.interrupt;

public class InterruptDemo {

    public static void main(String[] args) {
        Thread loop = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //判断当前线程的中断标识
                    System.out.println("Thread.isInterrupted()--" + Thread.currentThread().isInterrupted());
                    Thread.sleep(2000);
                } catch (InterruptedException e) { //这里会进行检测flag的值，判断是否抛出 InterruptedException 异常,并置flag=false
                    System.out.println("InterruptedException--"+ Thread.currentThread().isInterrupted());
                    System.out.println("Thread.interrupted()--" + Thread.interrupted()); //flag = false
                    System.err.println("被中断了额");
                    System.out.println("Thread.isInterrupted()after--" + Thread.currentThread().isInterrupted());
                    e.printStackTrace();
                    Thread.currentThread().interrupt(); //flag = true

                    System.out.println("===========");
                    boolean c = Thread.interrupted();  //返回当前，然后在置为false
                    boolean d = Thread.interrupted();
                    System.out.println("c=" + c);
                    System.out.println("d=" + d);
                }
            }
        });

        loop.start();
        loop.interrupt(); //标记中断 flag = true
    }
}
