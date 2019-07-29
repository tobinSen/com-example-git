package com.example.spring.jedis.test;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

public class JedisOfStringTest {

    private final Jedis jedis = new Jedis("127.0.0.1", 6379);

    @Test
    public void test1() {
        System.out.println(jedis.keys("*"));
        System.out.println(jedis.exists("key"));
        System.out.println(jedis.move("key", 1));
        System.out.println(jedis.expire("key", 10));
        System.out.println(jedis.ttl("key"));
        System.out.println(jedis.type("key"));
    }

    @Test
    public void testString() {
        //set, get, del, append, strlen
        System.out.println(jedis.set("key1", "value1"));
        System.out.println(jedis.del("key1"));
        System.out.println(jedis.append("key2", "value1"));//表示在原来的key上，进行字符串的拼接，没有就添加
        System.out.println(jedis.strlen("key1"));
        System.out.println(jedis.strlen("key2"));
        System.out.println(jedis.strlen("ley")); //返回value字符串的长度
    }
    @Test
    public void testString1() {
        //incr, incyby, decr, decyby
        System.out.println(jedis.set("key1", "1"));
        //System.out.println(jedis.incr("key1")); //自加
        //System.out.println(jedis.incrBy("key1", 3)); //越级自加
        System.out.println(jedis.decr("key1")); //自减
        System.out.println(jedis.decrBy("key1", 3)); //越级自减
    }
    @Test
    public void testString2() {
        System.out.println(jedis.set("key1", "value1"));
        //getrange, setrange
        System.out.println(jedis.getrange("key1", 0, -1));//字符串截取，左右都包含(endOffset=-1表名是最后的)
        System.out.println(jedis.setrange("key1", 0, "key1"));//这里进行替换的时候，保证了字符串的长度是固定的，所以会替换掉原来的值
        System.out.println(jedis.get("key1"));
    }
    @Test
    public void testString3() throws Exception{
        //setex, setnx
        //System.out.println(jedis.setex("key1", 3, "value1"));
        //Thread.sleep(4);
        System.out.println(jedis.get("key1"));
        System.out.println(jedis.setnx("key2", "value1"));
        System.out.println(jedis.get("key2"));
        System.out.println(jedis.setnx("key2", "value2"));
        System.out.println(jedis.get("key2"));
    }
    @Test
    public void testString4() throws Exception{
        //mset, mget, msetnx
        System.out.println(jedis.mset("key1", "value1", "key2", "value2", "key3", "value3"));
        System.out.println(jedis.mget("key1", "key2", "key4")); //不存在的key，返回为null
        System.out.println(jedis.msetnx("key5", "value6", "key4", "value7"));//原子性有一个存在就不不执行
        System.out.println(jedis.mget("key1", "key2", "key4","key5"));
    }
    @Test
    public void testString5() throws Exception{
        //getset: 返回原来的值，然后设置新值
        System.out.println(jedis.getSet("key1", "value2"));//返回的是旧值，先get在set，原子性操作
    }




}