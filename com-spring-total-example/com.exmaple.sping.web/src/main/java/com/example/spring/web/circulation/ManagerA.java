package com.example.spring.web.circulation;

/**
 * runtimeReference
 *
 *  a -> b -> c
 *
 */
public class ManagerA {

    private ManagerB serviceB;

    public ManagerA() {
        System.out.println("manager a 初始哈");
    }

    public void setServiceB(ManagerB serviceB) {
        this.serviceB = serviceB;
    }
}
