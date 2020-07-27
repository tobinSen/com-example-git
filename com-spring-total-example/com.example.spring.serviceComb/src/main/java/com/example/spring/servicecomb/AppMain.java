package com.example.spring.servicecomb;


import org.apache.servicecomb.foundation.common.utils.BeanUtils;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableServiceComb
public class AppMain {

    public static void main(String[] args) {
        BeanUtils.init();
    }
}
