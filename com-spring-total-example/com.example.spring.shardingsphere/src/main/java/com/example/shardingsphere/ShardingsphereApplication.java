package com.example.shardingsphere;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;

@SpringBootApplication(exclude = JtaAutoConfiguration.class)
@MapperScan(basePackages = {"com.snowalker.shardingjdbc.snowalker.demo.mapper",
        "com.snowalker.shardingjdbc.snowalker.demo.complex.sharding.mapper"}
)
@EnableEncryptableProperties
public class ShardingsphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingsphereApplication.class, args);
    }
}
