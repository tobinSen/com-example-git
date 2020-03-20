package com.example.spring.layui.reflect;

import java.lang.reflect.Field;

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
