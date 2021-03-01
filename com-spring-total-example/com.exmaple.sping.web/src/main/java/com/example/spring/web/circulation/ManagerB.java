package com.example.spring.web.circulation;

public class ManagerB {

    private ManagerC managerC;

    public ManagerB() {
        System.out.println("serviceB开始初始哈...");
    }

    public void setManagerC(ManagerC managerC) {
        this.managerC = managerC;
    }
}
