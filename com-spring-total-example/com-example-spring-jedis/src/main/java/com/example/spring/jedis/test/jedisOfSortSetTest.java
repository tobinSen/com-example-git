package com.example.spring.jedis.test;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

public class jedisOfSortSetTest {

    private final Jedis jedis = new Jedis("127.0.0.1", 6379);

    @Test
    public void testSortSet() {
        //zadd, zrange, zcard, zcount zrem(删除)
//        jedis.zadd("z1", 90, "优秀");
//        jedis.zadd("z1", 80, "良好");
//        jedis.zadd("z1", 70, "中等");
//        jedis.zadd("z1", 60, "及格");
        System.out.println(jedis.zrange("z1", 0, -1));//[及格, 中等, 良好, 优秀]
        System.out.println(jedis.zcard("z1")); //多少个数
        System.out.println(jedis.zcount("z1", 60, 70)); //分数之间有几个数
    }

    @Test
    public void testSortSe1() {
        //System.out.println(jedis.zrangeWithScores("z1", 0, -1));
        //zrank, zscore
        System.out.println(jedis.zrank("z1", "优秀")); //返回值的下标
        System.out.println(jedis.zscore("z1", "优秀")); //返回值的分数
    }

    @Test
    public void testSortSe2() {
        //zrevranke, zrevrange
        System.out.println(jedis.zrevrange("z1", 0, 2)); //逆序返回范围值
        System.out.println(jedis.zrevrank("z1", "优秀")); //逆序返回下标值
        //zrevrangebyscore
        System.out.println(jedis.zrevrangeByScore("z1", 81, 74)); //[良好] //判断分数属于那个member
    }
}
