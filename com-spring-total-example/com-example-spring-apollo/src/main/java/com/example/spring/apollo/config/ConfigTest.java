package com.example.spring.apollo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableApolloConfig
//@RefreshScope
public class ConfigTest {

    private final static Logger logger = LoggerFactory.getLogger(ConfigTest.class);

    @Value("${java_apollo}")
    private String javaApollo;

    //@ApolloConfig("application")
    //private Config config1;

    public String getJavaApollo() {

        if (logger.isDebugEnabled()) {
            logger.info("{}", "is debug enable");
        }

       // Config config = ConfigService.getAppConfig();
       // String property = config.getProperty("java_apollo", "null");
       // System.out.println(property);
        System.out.println("-------------");
        //System.out.println(config1.getProperty("java_apollo", null));
        return javaApollo;
    }
}
