package com.example.spring.graphql.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Book {
    private Integer id;
    private String name;
    private Author author;
    private String publisher;
}
