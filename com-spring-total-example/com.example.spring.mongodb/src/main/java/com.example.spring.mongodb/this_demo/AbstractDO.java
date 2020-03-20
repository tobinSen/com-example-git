package com.example.spring.mongodb.this_demo;

import java.util.List;
import java.util.Map;

public interface AbstractDO<T extends Animal & Cat> { //这里的第二个必须是一个接口


    default <K extends Animal & Cat, T extends Cat> Map<T, K> test(List<K> list, T t) { //泛型方法声明泛型类型
        return null;
    }

}
