package com.uplooking.fastJson;

import com.fasterxml.jackson.annotation.*;
import com.uplooking.mongo.Book;

import java.util.Date;

@JsonPropertyOrder(alphabetic = true)//根据字母首字母进行排序
@JsonInclude(JsonInclude.Include.NON_NULL)//序列化不为null的值
// @JsonFormat(shape = JsonFormat.Shape.OBJECT)//将枚举转为对象
public class Person {

    @JsonProperty(value = "id_id")
    private Long id;

    private String name;
    @JsonIgnoreProperties(value = {"age"})
    private Integer age;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT-8")
    private Date time;
    @JsonUnwrapped//当实体类中成员属性是一个类的对象时候，忽略包装。直接显示属性。
    private Book book;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", time=" + time +
                '}';
    }
}
