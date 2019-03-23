package com.uplooking.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Document(collection = "book")
@Data
public class Book {

    @Indexed
    private Long id;
    private String title;
    private String context;
    private Timestamp time;

    public void gitBrach01() {
        System.out.println("gitBrach01");
    }

    private String newTitle;
    private String newContext;

    private String newName;

    private Long oldTitle;
    private String oldName;

    private Long oldNewName;
    private Long oldNew;


    public void gitBrach02() {
        System.out.println("gitBrach02");
    }
}
