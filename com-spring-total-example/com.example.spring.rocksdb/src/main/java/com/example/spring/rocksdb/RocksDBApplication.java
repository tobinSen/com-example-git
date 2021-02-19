package com.example.spring.rocksdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RocksDBApplication {

    public static void main(String[] args) {
        SpringApplication.run(RocksDBApplication.class, args);
//        System.out.println("用户的当前工作目录:" + System.getProperty("user.dir"));
    }
}
