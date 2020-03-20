package com.example.spring.jedis.test;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

public class JedisOfListTest {

    private final Jedis jedis = new Jedis("127.0.0.1", 6379);

    @Test
    public void testList1() {
        //lpush, rpush, lrange
        System.out.println(jedis.lpush("key4", "value4", "value4", "value4"));//从左边添加,list不能进行覆盖，一直追加到里面
        System.out.println(jedis.rpush("key6", "value6", "value6", "value6"));
        System.out.println(jedis.lrange("key4", 0, -1)); //左右都包含
    }

    @Test
    public void testList2() {
        //lpop, rpop, rpoplpush
//        System.out.println(jedis.lpop("key6")); //从左边删除第一个，并返回
//        System.out.println(jedis.lrange("key6", 0, -1));
//        System.out.println(jedis.lpop("key6"));
//        System.out.println(jedis.lrange("key6", 0, -1)); //从右边删除第一个，并返回
        System.out.println(jedis.lrange("key4", 0, -1));
        System.out.println(jedis.rpoplpush("key4", "key6"));//将key4右边的第一个添加懂啊key6从左边添加进去
        System.out.println(jedis.lrange("key6", 0, -1));
    }

    @Test
    public void testList3() {
        //lindex, llen
        System.out.println(jedis.lindex("key6", 0));
        System.out.println(jedis.lindex("key6", -1));
        System.out.println(jedis.llen("key6"));
    }

    @Test
    public void testList4() {
        //lrem
//        System.out.println(jedis.lpush("key7", "value7", "value7", "value7"));
//        System.out.println(jedis.lrem("key7", 2, "value7"));//删除key7中2个value7的值
        System.out.println(jedis.lrange("key7", 0, -1));
    }

    @Test
    public void testList5() {
        //ltrim
        //System.out.println(jedis.lpush("key7", "value7", "value7", "value7"));
        System.out.println(jedis.lrange("key7", 0, -1));
        System.out.println(jedis.ltrim("key7", 0, 0)); //对key7中的list值就行截取，然后赋值到源list中
        System.out.println(jedis.lrange("key7", 0, -1));
    }

    @Test
    public void testList6() {
        //lset
        System.out.println(jedis.lset("key7", 0, "value7-1"));//指定索引位置下修改值
        System.out.println(jedis.lrange("key7", 0, -1));
    }
    @Test
    public void testList7() {
        //linsert
        //在指定的值后面或者前面添加值
        //System.out.println(jedis.linsert("key7", BinaryClient.LIST_POSITION.AFTER, "value7-3", "value7-2"));
        System.out.println(jedis.lrange("key7", 0, -1));
    }


}
