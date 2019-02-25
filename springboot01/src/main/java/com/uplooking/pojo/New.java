package com.uplooking.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.Date;

@Document(indexName = "springbootdb",type = "new")
@Data
public class New {

    @Id
    private Long id;
    private String title;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyyMMdd HH:mm:ss:SSS")
    @Field
    @CreatedDate
    //@LastModifiedBy
    private Date createDateTime;
}
