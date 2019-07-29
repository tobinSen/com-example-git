package com.example.spring.jedis.test;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.*;

import java.util.List;
import java.util.Set;

public class JedisOfFutureTest {

    private final Jedis jedis = new Jedis("127.0.0.1", 6379);

    @Test
    public void futureTest() throws Exception {

        Pipeline pipelined = jedis.pipelined();//获取管道
        System.out.println(pipelined);
        Response<List<String>> key1 = pipelined.mget("key1", "key3");
        pipelined.sync();
        System.out.println(key1.get());

        pipelined.mset("key1", "value", "key2", "value");
        pipelined.sync();

        pipelined.close();
        jedis.close();

    }

    //模糊查询
    @Test
    public void futureTest1() throws Exception {
//        jedis.set("set1", "set1");
//        jedis.set("set2", "set2");
//        jedis.set("set3", "set3");
        System.out.println(jedis.mget("set1", "set2", "set2"));
        Set<String> keys = jedis.keys("set*");
        System.out.println(keys.toString());
    }

    //游标
    @Test
    public void futureTest2() throws Exception {
        ScanParams scanParams = new ScanParams();
        scanParams.match("set*");
        scanParams.count(500); //匹配的字符长度
        ScanResult<String> scan = jedis.scan("1", scanParams);
        System.out.println(scan.getStringCursor());//返回0 说明遍历完成说明没有数据
        System.out.println(scan.getResult().toString());
    }
}
