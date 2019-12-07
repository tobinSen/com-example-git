package com.example.spring.elasticsearch.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@Document(indexName = "province", type = "city")//数据库 + 表名
public class City implements Serializable {

    @Id
    private Long id;
    //keyword：存储数据时候，不会分词建立索引
    //text：存储数据时候，会自动分词，并生成索引
    @Field(type = FieldType.keyword)
    private String score;
    @Field(type = FieldType.keyword)
    private String name;
    private String description;
    @Field(type = FieldType.Date, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date timestamp;
}
