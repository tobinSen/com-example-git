package com.uplooking.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
//@Configuration //注入属性的同时，注册到spring容器中
@ConfigurationProperties(prefix = "person") //prefix表示将配置类中以什么开头的配置信息注入到实体的属性中
public class Person {

    //@Value("${person.name}")
    //@Length(max = 1,message = "提示错误")
    private String name;
    @Value("${person.age}")
    private Integer age;
    @Value("${person.boss}")
    private Boolean boss;
    @Value("${person.birth}")
    private Date birth;
    @Value("#{'${person.gender}'.split(',')}") //SPEL表达式形式获取集合
    private List<String> genders;

    //@Value("#{${person.maps}}")
    private Map<String, Object> maps;
    //@Value("#{${person.lists}}")
    private List<String> lists;
    //@Value("#{${person.dog}}")  不支持复杂类型 @configurationProperties支持复杂类型，但是@Value可以使用spel表达式形成的数组映射到集合中
    private Dog dog;

    protected Long parentId;

    public String parentBook;
}
