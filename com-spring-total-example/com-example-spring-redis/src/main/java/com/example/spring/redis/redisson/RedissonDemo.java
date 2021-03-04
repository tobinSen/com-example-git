package com.example.spring.redis.redisson;

import org.junit.jupiter.api.Test;
import org.redisson.Redisson;
import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RedissonDemo {

    /**
     * "EVAL"
     * "local expiredValues = redis.call('zrangebyscore', KEYS[2], 0, ARGV[1], 'limit', 0, ARGV[2]);
     * if #expiredValues > 0 then
     *   for i, v in ipairs(expiredValues) do
     *     local randomId, value = struct.unpack('dLc0', v);
     *     redis.call('rpush', KEYS[1], value);                 //blockQueue中插入值 右向左 blpop (一次性的)
     *     redis.call('lrem', KEYS[3], 1, v);                   //redisson_delay_queue 删除
     *   end;
     *   redis.call('zrem', KEYS[2], unpack(expiredValues));
     * end;
     * local v = redis.call('zrange', KEYS[2], 0, 0, 'WITHSCORES');
     * if v[1] ~= nil then
     *   return v[2];
     * end return nil;
     *
     * " "3" "blockQueue" "redisson_delay_queue_timeout:{blockQueue}" "redisson_delay_queue:{blockQueue}" "1614842662110" "100"
     *
     *
     * 1614842662110 就是时间戳
     *
     *
     * local value = struct.pack('dLc0', tonumber(ARGV[2]), string.len(ARGV[3]), ARGV[3]);
     * redis.call('zadd', KEYS[2], ARGV[1], value);
     * redis.call('rpush', KEYS[3], value);
     * local v = redis.call('zrange', KEYS[2], 0, 0);
     * if v[1] == value then
     *   redis.call('publish', KEYS[4], ARGV[1]);
     * end;
     *
     * blockQueue redisson_delay_queue_timeout:{blockQueue}  redisson_delay_queue:{blockQueue}   redisson_delay_queue_channel:{blockQueue}
     *
     *
     * HashedWheelTimer 基于时间轮进行的  客户端基于时间轮进行拉取消息
     */
    @Test
    public void producer() {
        RedissonClient client = init();
        RBlockingQueue<Object> queue = client.getBlockingQueue("blockQueue");
        RDelayedQueue<Object> delayedQueue = client.getDelayedQueue(queue);

        /**
         * 1.创建了一个list key=blockQueue
         * 2.redisson_delay_queue_channel:{dest_queue1} 订阅channel
         * 3.redisson_delay_queue:{blockQueue} hashTag list --> 对value封装
         * 4.redisson_delay_queue_timeout:{blockQueue} zset --> value -> score关系
         */
        for (int i = 0; i < 10; i++) {
            delayedQueue.offer("delayed" + i, i, TimeUnit.SECONDS);
        }
    }

    @Test
    public void consumer() {
        RedissonClient client = init();
        RBlockingQueue<Object> queue = client.getBlockingQueue("blockQueue");
        while (true) {
            Object obj = null;
            try {
                obj = queue.take();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("订单取消时间：" + new SimpleDateFormat("hh:mm:ss").format(new Date()) + "==订单生成:" + obj);
        }
    }

    private RedissonClient init() {
        Config config = new Config();
        config.setCodec(new JsonJacksonCodec());//将对象进行序列化和反序列化
        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(1);
        return Redisson.create(config);
    }
}
