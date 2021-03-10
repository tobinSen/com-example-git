package com.example.spring.jedis.test.delay;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Tuple;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * 延时任务实现方案：
 * 1、quartz --> 什么时候开始执行
 * 2、timeHashWheel --> 时间轮
 * 3、RabbitMQ --> 延时消息
 * 4、delayQueue --> 延时队列
 * 5、schedule --> 延时开始执行
 * 6、sortSet  --> score 排名
 */
public class RedisDelayQueue {

    private JedisPool jedisPool = null;
    // Redis服务器IP
    private String ADDR = "10.208.20.36";
    // Redis的端口号
    private int PORT = 8080;

    private static String DELAY_QUEUE = "delayqueue";

    private Jedis jedis;

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public RedisDelayQueue() {
        jedisPool = new JedisPool(new JedisPoolConfig(), ADDR, PORT, 10000, "9yyt4swskizudmgc");
        jedis = jedisPool.getResource();
    }

    public static void main(String[] args) throws InterruptedException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        RedisDelayQueue queue = new RedisDelayQueue();
        final Jedis jedis = queue.jedis;
        // 1.先判断是否存在 setnx + expire --> 第一次进行  第二次
        // 2.incr进行判断 -->
        String key = dateFormat.format(new Date()) + "0000"; //


        for (int i = 0; i < 10; i++) { //
//            new Thread(() -> {
//            if (!jedis.exists("Date:" +key)) {
//                jedis.expire("Date:" + key, 10);
//                jedis.set("Date:" + key, key);
//            }
            Long sequence = jedis.incr("Date:" + key);
            System.err.println(sequence);
//            }).start();
        }
        Thread.currentThread().join();

//        RedisDelayQueue redisDelay = new RedisDelayQueue();
//        redisDelay.pushOrderQueue();
//        for (int i = 0; i < 3; i++) {
//            new Thread(redisDelay::pollOrderQueue).start();
//        }
////        redisDelay.deleteZSet();
    }

    public void deleteZSet() {
        Jedis jedis = jedisPool.getResource();
        jedis.del(DELAY_QUEUE);
    }

    /**
     * 消息入队
     */
    public void pushOrderQueue() {
        Jedis jedis = jedisPool.getResource();
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.SECOND, 10); // 延迟10秒
        int order1 = (int) (cal1.getTimeInMillis() / 1000);

        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.SECOND, 20); // 延迟20秒
        int order2 = (int) (cal2.getTimeInMillis() / 1000);

        Calendar cal3 = Calendar.getInstance();
        cal3.add(Calendar.SECOND, 30);
        int order3 = (int) (cal3.getTimeInMillis() / 1000);

        jedis.zadd(DELAY_QUEUE, order1, "order1");
        jedis.zadd(DELAY_QUEUE, order2, "order2");
        jedis.zadd(DELAY_QUEUE, order3, "order3");
        System.out.println(sdf.format(new Date()) + " add finished.");
    }

    /**
     * 消费消息
     */
    public void pollOrderQueue() {

        Jedis jedis = jedisPool.getResource();
        while (true) {

            Long lockId;
            try {
                lockId = jedis.setnx("tong", DELAY_QUEUE);
                if (lockId == 1) {
                    Set<Tuple> set = jedis.zrangeWithScores(DELAY_QUEUE, 0, 0); //获取一个

                    if (set == null || set.isEmpty()) {
                        System.out.println("当前没有等待的任务");
                        continue;
                    }
                    // 获取值 和 score
                    String value = ((Tuple) set.toArray()[0]).getElement();
                    int score = (int) ((Tuple) set.toArray()[0]).getScore();

                    Calendar cal = Calendar.getInstance();
                    int nowSecond = (int) (cal.getTimeInMillis() / 1000);

                    // redis score 和 当前时间相同 就执行
                    if (score <= nowSecond) {
                        // 这里删除表示 这个任务执行完了
                        jedis.zrem(DELAY_QUEUE, value);

                        System.out.println(sdf.format(new Date()) + " removed key:" + value);
                    }

                    if (jedis.zcard(DELAY_QUEUE) <= 0) {
                        System.out.println(sdf.format(new Date()) + " zset empty ");
                        return;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                jedis.del("tong");
            }
        }
    }


}
