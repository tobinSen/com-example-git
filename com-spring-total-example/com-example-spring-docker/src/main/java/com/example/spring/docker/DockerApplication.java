package com.example.spring.docker;


/**
 *
 *                 Java编译器
 *  ----------------------------------------------------
 * | 词 | 语 |             | 语 |            |           |
 * | 法 | 法 |语法/抽象语法树 | 义 |注解抽象语法树 |字节码生成器 | -->字节码(xx.class)
 * | 分 | 分 |             | 分 |            |           |
 * | 析 | 析 |             | 析 |            |           |
 *  ----------------------------------------------------
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DockerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerApplication.class, args);
    }

    @GetMapping("/{name}")
    public String hi(@PathVariable(value = "name") String name) {
        return "hi , " + name;
    }
}
