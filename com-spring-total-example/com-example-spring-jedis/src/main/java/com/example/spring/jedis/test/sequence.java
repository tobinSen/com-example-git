package com.example.spring.jedis.test;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class sequence {

    private Jedis jedis;

    @Before
    public void before() {
        jedis = new JedisPool(new JedisPoolConfig(), "10.208.20.36", 8080, 10000, "9yyt4swskizudmgc").getResource();
    }

    @Test
    public void test() throws ParseException {
        for (int i = 0; i < 100000; i++) {
            // prefix + yyyMMdd + 四位0000
            String keyPrefix = "redis:{0}";
            String key = MessageFormat.format(keyPrefix, new SimpleDateFormat("yyyMMddHHmm").format(new Date()));
            // 获取到了
            if (1 == jedis.setnx(key, "0")) {
                jedis.expireAt(key, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2021-03-10 18:43:40").getTime());
            }
            // 自增
            String sequence = String.format("%s%04d", key, jedis.incr(key));
            System.err.println(sequence);
        }
    }


    // 获得某天最大时间 2020-02-19 23:59:59
    public static Date getEndOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime endOfDay = localDateTime.with(LocalTime.MAX);
        return Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 获得某天最小时间 2020-02-17 00:00:00
    public static Date getStartOfDay(Date date) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault());
        LocalDateTime startOfDay = localDateTime.with(LocalTime.MIN);
        return Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
    }

}
