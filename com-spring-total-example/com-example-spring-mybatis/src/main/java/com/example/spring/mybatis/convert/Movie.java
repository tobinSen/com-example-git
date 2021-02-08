package com.example.spring.mybatis.convert;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Movie {
    private String name;
    private int seat;

}
