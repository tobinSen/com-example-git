package com.example.spring.jedis.test;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

public class JedisOfSetTest {

    private final Jedis jedis = new Jedis("127.0.0.1", 6379);

    @Test
    public void testSet() {
        //sadd, smembers, sismember, scard
        //存储的时候会去重
        System.out.println(jedis.sadd("key8", "value8", "value8", "value9", "value9"));
        System.out.println(jedis.smembers("key8")); //获取key对应的set集合值
        System.out.println(jedis.sismember("key8", "value8")); //判断是否存在这个成员的值
        System.out.println(jedis.scard("key8")); //统计key8里值的个数
    }

    @Test
    public void testSet1() {
        //srem, srandmember, spop
        System.out.println(jedis.srem("key8", "value8")); //删除指定的值
        System.out.println(jedis.smembers("key8")); //获取key对应的set集合值
        System.out.println(jedis.srandmember("key8")); //随机获取一个值
        System.out.println(jedis.spop("key8")); //删除第一个值
    }

    @Test
    public void testSet2() {
        //sdiff, sinter, sunion
//        jedis.sadd("key8", "value8","value8");
        jedis.sadd("key9", "value9", "value10");
        System.out.println(jedis.smembers("key8"));
        System.out.println(jedis.smembers("key9"));
        System.out.println(jedis.sdiff("key8", "key9")); //在key8里面，而不在key9里
        System.out.println(jedis.sinter("key8", "key9")); //交集
        System.out.println(jedis.sunion("key8", "key9")); //并集

    }

}
