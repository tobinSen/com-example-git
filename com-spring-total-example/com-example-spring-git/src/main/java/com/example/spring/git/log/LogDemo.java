package com.example.spring.git.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogDemo {

    /**
     * 1、只配置了<root></root>标签
     * 2、添加了<logger></logger>多个logger匹配的时候，只会匹配更加精确的
     * 3、appender
     *      ConsoleAppender
     *      FileAppender
     *      RollingFileAppender
     *          rollingPolicy
     *              TimeBasedRollingPolicy    -->时间
     *              FixedWindowRollingPolicy  -->固定窗口
     *          triggeringPolicy
     *              SizeBasedTriggeringPolicy -->大小
     *
     */

    private static Logger log = LoggerFactory.getLogger(LogDemo.class);

    public static void main(String[] args) {
        log.trace("======trace");
        log.debug("======debug");
        log.info("======info");
        log.warn("======warn");
        log.error("======error");
    }
}
