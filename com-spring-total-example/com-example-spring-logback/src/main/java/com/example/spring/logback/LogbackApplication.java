package com.example.spring.logback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogbackApplication {

    /**
     * spring的默认日志是JUL,springboot默认SLF4J 结合 LogBack
     *
     * 常见日志框架： JUL, Log4j, Log4j2, Commons Loggin, Slf4j,
     *
     * 要为系统导入 SLF4J 的 jar（slf4j-api.jar） 和 日志框架的实现 jar.
     * 由于每一个日志的实现框架都有自己的配置文件，所以在使用 SLF4 之后，配置文件还是要【使用实现日志框架的配置文件】
     *
     * 1.springboot对日志框架的解决方案：
     *  slf4j-api.jar + 第三方的日志框架的实现类(每个框架的配置文件不一致) 【springboot默认实现logback框架】
     *
     * 2.slf4j对第三方框架内部日志的替换方案：
     *  1.排除第三方框架的日志
     *  2.引入slf4j-api.jar
     *  3.引入slf4j的第三方实现框架
     *
     *
     *  springboot为我们提供了一个规则，按照规则组织配置文件名，就可以被正确加载：
     *
     * Logback：logback-spring.xml, logback-spring.groovy, logback.xml, logback.groovy
     * Log4j：log4j-spring.properties, log4j-spring.xml,
     *        log4j.properties, log4j.xml
     * Log4j2：log4j2-spring.xml,
     *         log4j2.xml
     * JDK (Java Util Logging)：logging.properties
     *
     */

    public static void main(String[] args) {
        SpringApplication.run(LogbackApplication.class, args);
    }

}
