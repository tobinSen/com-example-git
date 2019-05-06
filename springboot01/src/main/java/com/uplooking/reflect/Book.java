package com.uplooking.reflect;

import com.uplooking.pojo.Person;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
public class Book extends Person implements Serializable {

    private Long id;
    public String bookName;
    private String author;
    private Timestamp publish;

}
