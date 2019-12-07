package com.example.spring.dynamic.aop.importAware;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.context.annotation.AutoProxyRegistrar;

public class RedissonHttpSessionConfiguration extends AdviceModeImportSelector<EnableRedissonHttpSession> {


    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
        switch (adviceMode) {
            case PROXY:
                //优先加载
                String[] s = new String[]{RedissonHttpSessionConfigurationAware.class.getName(), AutoProxyRegistrar.class.getName()};
                return s;
            case ASPECTJ:
            default:
                return null;
        }
    }
}
