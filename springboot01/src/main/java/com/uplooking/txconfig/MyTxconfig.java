package com.uplooking.txconfig;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

@EnableTransactionManagement
@Component
public class MyTxconfig {

    /**
     * 环境搭建
     *  1、导入相关依赖
            数据源，数据库驱动，spring-Jdbc模块
     *  2、配置数据源，JdbcTemplate（Spring提供的简化数据库操作的工具）
     *  3、给方法上标注@Transactional 表示当前方法是一个事务方法
     *  4、@EnableTransactionManagement 开启基于注解的事务管理功能
     *  5、配置事务管理器
     */

    @Transactional
    public void insert(){
        System.out.println("添加");
    }
}
