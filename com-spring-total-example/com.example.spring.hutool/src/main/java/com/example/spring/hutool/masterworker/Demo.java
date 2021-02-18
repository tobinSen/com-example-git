package com.example.spring.hutool.masterworker;

public class Demo {
    public static void main(String[] args) {
//        Master master = new Master(new MyWorker1(), 3);
//        master.submit(new Task());
//        master.execute();
//        System.out.println(master.isCompleted());
//        System.out.println("==============================");
//        System.out.println(master.getResult());

        Eval eval = new EvalImpl();
        System.out.println((char[]) eval.eval());

    }

}
