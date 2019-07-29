package com.example.spring.jedis.test;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class JedisOfHashTest {

    private final Jedis jedis = new Jedis("127.0.0.1", 6379);

    @Test
    public void testHash() {
        //hset, hget, hmset, hmget, hgetAll, hdel
//        System.out.println(jedis.hset("key11", "key11", "value11"));
//        System.out.println(jedis.hget("key11", "key11"));
        Map<String,String> map = new HashMap<>();
        map.put("map1", "1");
        map.put("map2", "1");
        map.put("map3", "1");
        System.out.println(jedis.hmset("map", map));
        System.out.println(jedis.hmget("map", "map1", "map2", "map3"));
        System.out.println(jedis.hgetAll("map"));
        System.out.println(jedis.hdel("map", "map1"));
        System.out.println(jedis.hgetAll("map"));
    }
    @Test
    public void testHash1() {
        //hlen, hexists
        System.out.println(jedis.hlen("map"));
        System.out.println(jedis.hexists("map", "map1"));
        //hkeys, hvals
        System.out.println(jedis.hkeys("map")); //获取指定key中的field字段
        System.out.println(jedis.hvals("map")); //获取指定key中的value值
        //hincrby, hsetnx
        System.out.println(jedis.hincrBy("map", "map2", 2));
        System.out.println(jedis.hgetAll("map"));
        System.out.println(jedis.hsetnx("map", "map2", "9"));
        System.out.println(jedis.hgetAll("map"));
    }

}
