package com.example.spring.log4j;

import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Random;

/**
 * 常见的日志框架：
 *  JUL (Java Util Log) 是 JDK 中自带的log功能， 使用很少，功能不完善且存在性能问题
 *  JCL (Java Commons Log) 是一个Log Facade(日志门面)只提供了Log Api 接口，不提供实现，
 *          然后由Adapter来使用Log4j或者JUL作为Log Implement日志实现。这个相当于是一个日志标准在运行的时候可以根据我们个人的喜好去选择真正的日志输出，
 *          如果配置了Log4j ，就会走Log4j ，什么都没有配置的话，默认就走JUL了。
 *  Log4j 是在Logback 出现之前被广泛使用的log lib，但是性能没有lo4j2和logback的性能好
 *  Log4j2 也做了Facade/Implement 分离的设计，分成了 log4j-api 和 log4j-core 但其实lo4j2和log4j没有太大的关系，而且并不兼容
 *  slf4j/logback slf4j(The Simple Logging for java) 其实和JCL一样都是一个Log Facade(日志门面)接口，而Logback就是slf4j的实现
 *
 *  三个流行的Log 接口（jcl,slf4j,log4j-api）和四个流行的Log实现（jul,log4j,logback,log4j-core）
 *
 * 项目实践：
 *  1. 使用slf4j承接不同的日志框架，然后把不同的日志输出转换为slf4j的实现方式(日志框架-slf4j)
 *  2. 适配器名称	        原日志框架	            提供方	            实现方式
 *  jcl-over-slf4j	    apache commons-logging	    slf4j	    jcl-over-slf4j重写了commons-logging的Log和LogFactory类，做了不同的实现
 *  jul-to-slf4j	    java jdk-logging	        slf4j	    jul-to-slf4j 下有 SLF4JBridgeHandler实现，系统启动的时候调用SLF4JBridgeHandler.removeHandlersForRootLogger();删除所有的Logger，然后调用SLF4JBridgeHandler.install();装载上SLF4J
 *  log4j-over-slf4j	apache log4j 1	            slf4j	    log4j-over-slf4j 重写了log4j 1 的Logger和LogFactory类，做了不同的实现
 *  log4j-to-slf4j	    apache Log4j 2	            slf4j       log4j-to-slf4j 使用OSGI SPI的形式为org.apache.logging.log4j.spi.Provider提供了SLF4J的实现
 *
 * SLF4J对接不同的日志框架实现（SLF4J—>日志框架）
 *
 *  slf4j-api --> slf4j-jdk14.jar --> JUC
 *
 *  slf4j-api --> slf4j-log4j12.jar --> log4j.jar
 *
 *  slf4j-api --> log4j-slf4j-impl --> log4j-api.jar
 *                                     log4j-core.jar
 *
 *  slf4j-api --> logback-classic.jar
 *                logback-core.jar
 *
 * 总结：5,3点
 *
 */
@RestController
public class Log4jCongroller {

    private static Logger logger = Logger.getLogger(Log4jCongroller.class);

    @RequestMapping("log4j.do")
    public String log4j(String name) throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("randomNum", new Random().nextInt(100));
        logger.info(jsonObject);
        ModelAndView mv = new ModelAndView();
        mv.addAllObjects(jsonObject);
        return "template/index.html";
    }

}
