package com.example.spring.hutool.core;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class IDDemo {
    public static void main(String[] args) {
        //参数1为终端ID
//参数2为数据中心ID
        Snowflake snowflake = IdUtil.createSnowflake(0, 0);
        System.out.println(snowflake.nextId());
        System.out.println(snowflake.nextId());
        System.out.println(snowflake.nextId());
        System.out.println(snowflake.nextId());
        System.out.println(snowflake.nextId());
        System.out.println(snowflake.nextId());
        System.out.println(snowflake.nextId());
        System.out.println(snowflake.nextId());
        System.out.println(snowflake.nextId());
        System.out.println(snowflake.nextId());


        Dict dict = Dict.create()
                .set("key1", 1)//int
                .set("key2", 1000L)//long
                .set("key3", DateTime.now());//Date

    }
}
