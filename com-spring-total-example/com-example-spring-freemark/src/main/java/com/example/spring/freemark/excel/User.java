package com.example.spring.freemark.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.util.Date;

public class User {

    private int id;

    @Excel(name = "姓名", isImportField = "name")
    private String name;

    @Excel(name = "性别", isImportField = "sex", replace = {"男_1", "女_2", "保密_0"})
    private int sex;

    @Excel(name = "年龄", isImportField = "age")
    private int age;

    @Excel(name = "创建时间", orderNum = "4", mergeVertical = true, databaseFormat = "yyyy-MM-dd HH:mm:ss", format = "yyyy-MM-dd HH:mm:ss", isImportField = "createTime")
    private Date createTime = new Date();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
