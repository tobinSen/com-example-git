package com.uplooking.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Timestamp;

@Document(collection = "book")
@Data
public class Book {

    private Long id;
    private String title;
    private String context;
    private Timestamp time;
}
