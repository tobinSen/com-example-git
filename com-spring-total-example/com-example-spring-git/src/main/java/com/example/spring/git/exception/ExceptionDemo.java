package com.example.spring.git.exception;

/**
 * java的异常处理
 * 1、手动抛异常是为了，让调用者显示的进行有捕获的行为
 * 2、如果没有显示的进行抛异常，程序会根据自身的异常，一直向上抛，知道有捕获可以拦截，如果没有捕获拦截就抛到虚拟机
 */
public class ExceptionDemo {

    public static void main(String[] args) {
        try {
            myExcetion();
        } catch (MyException e) {
            e.printStackTrace();
        }
    }

    public static void myExcetion() throws MyException {
        otherException();
    }

    public static void otherException(){
        System.out.println(1 / 0);
    }
}
