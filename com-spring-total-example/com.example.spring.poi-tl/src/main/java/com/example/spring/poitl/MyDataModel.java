package com.example.spring.poitl;

import com.deepoove.poi.el.Name;

public class MyDataModel {

    // 模板标签{{name}}
    private String name;
    // 模板标签{{start_time}}
    @Name("start_time")
    private String startTime;
    // {{author.XXX}}
    private Author author;

    public MyDataModel(String name, String startTime, Author author) {
        this.name = name;
        this.startTime = startTime;
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
