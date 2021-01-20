package com.example.spring.graphql.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Author {

    private Integer id;
    private String name;
    private Integer age;

}
