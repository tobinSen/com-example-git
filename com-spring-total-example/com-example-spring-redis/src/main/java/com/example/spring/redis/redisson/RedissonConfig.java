package com.example.spring.redis.redisson;

import org.redisson.Redisson;
import org.redisson.api.*;
import org.redisson.api.annotation.REntity;
import org.redisson.api.annotation.RId;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.connection.balancer.RandomLoadBalancer;
import org.redisson.liveobject.resolver.UUIDGenerator;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class RedissonConfig {


    @Configuration
    @ComponentScan
    @EnableCaching
    public static class Application {
        @Bean(destroyMethod = "shutdown")
        RedissonClient redisson() throws IOException {
            Config config = new Config();
            config.useClusterServers()
                    .addNodeAddress("127.0.0.1:7004", "127.0.0.1:7001");
            return Redisson.create(config);
        }

        @Bean
        CacheManager cacheManager(RedissonClient redissonClient) {
            Map<String, CacheConfig> config = new HashMap<String, CacheConfig>();
            // 创建一个名称为"testMap"的缓存，过期时间ttl为24分钟，同时最长空闲时maxIdleTime为12分钟。
            config.put("testMap", new CacheConfig(24 * 60 * 1000, 12 * 60 * 1000));
            return new RedissonSpringCacheManager(redissonClient, config);
        }


    }
//
//    @Bean
//    public RedissonConnectionFactory redissonConnectionFactory(RedissonClient redisson) {
//        return new RedissonConnectionFactory(redisson);
//    }
//
//    @Bean(destroyMethod = "shutdown")
//    public RedissonClient redisson(@Value("classpath:/redisson.yaml") Resource configFile) throws IOException {
//        Config config = Config.fromYAML(configFile.getInputStream());
//        return Redisson.create(config);
//    }

    @REntity
    public static class Customer {

        @RId(generator = UUIDGenerator.class)
        protected String id;

        protected String name;

        protected String address;

        protected String phone;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }

//    static {
//        Config config = new Config();
//        config.useSingleServer();//单机模式
//        config.useMasterSlaveServers();//主备
//        config.useClusterServers();//集群
//        config.useSentinelServers();//哨兵
//        config.useReplicatedServers();//分片
//    }

    private static Config configSimple() {
        Config config = new Config();
        config.setCodec(new JsonJacksonCodec());//将对象进行序列化和反序列化
        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setDatabase(1);
//        config.setThreads(Runtime.getRuntime().availableProcessors() * 2);//线程池数量被所有RTopic对象监听器
//        config.setNettyThreads(Runtime.getRuntime().availableProcessors() * 2);
//        config.setExecutor(Executors.newSingleThreadExecutor());
////        config.setEventLoopGroup(new NioEventLoopGroup ());
//        config.setTransportMode(TransportMode.NIO);
//        config.setLockWatchdogTimeout(30000);
//        config.setKeepPubSubOrder(true);//发布订阅执行顺序
//        config.setP
        return config;
    }

    private static Config configCluter() {
        Config config = new Config();
        config.useClusterServers().setScanInterval(2000)
                //可以用"rediss://"来启用SSL连接
                .addNodeAddress("redis://127.0.0.1:7000", "redis://127.0.0.1:7001")
                .addNodeAddress("redis://127.0.0.1:7002")
                .setLoadBalancer(new RandomLoadBalancer())
                .setSubscriptionConnectionMinimumIdleSize(1000)
                .setSubscriptionConnectionPoolSize(50)
                .setSlaveConnectionPoolSize(100)
                .setSlaveConnectionMinimumIdleSize(100)
                .setMasterConnectionMinimumIdleSize(100)
                .setMasterConnectionPoolSize(100)
                .setIdleConnectionTimeout(10000)
                .setConnectTimeout(10000)
                .setTimeout(10000)
                .setRetryAttempts(3)
                .setRetryInterval(2000);

        return config;
    }

    public static RedissonClient redissonClientSimple() throws Exception {
        return Redisson.create(configSimple());
    }

    public static void main01(String[] args) throws Exception {
        RedissonClient client = redissonClientSimple();
        RBucket<Object> bucket = client.getBucket("bucket");
        bucket.set("bucket-001", 10000, TimeUnit.MILLISECONDS);
//        boolean exist = bucket.trySet("bucket-002"); //setnx
//        System.out.println(exist);

//        bucket.compareAndSet("bucket-001", "bucket-002");//原子性操作

        bucket.addListener(new ExpiredObjectListener() {
            @Override
            public void onExpired(String name) {
                System.err.println(name);
            }
        });
        bucket.addListener(new DeletedObjectListener() {
            @Override
            public void onDeleted(String name) {
                System.err.println(name);
            }
        });


        //client.shutdown();

    }

    public static void main02(String[] args) throws Exception {
        RedissonClient client = redissonClientSimple();
        /*RAtomicLong atomicLong = client.getAtomicLong("atomic_1");
        long l = atomicLong.addAndGet(4);*/
        //bitSet是一个数组，每个数组的值都是boolean类型
        RBitSet bitSet = client.getBitSet("bitSet_1");
        bitSet.set(1, true);
//        bitSet.set(, );
//        bitSet.set(2, 4, false);
//        bitSet.set(4, 7);
    }

    public static void main03(String[] args) throws Exception {
        RedissonClient client = redissonClientSimple();

//        RClusteredBloomFilter<SomeObject> bloomFilter = client.getClusteredBloomFilter("sample");
//        // 采用以下参数创建布隆过滤器
//        // expectedInsertions = 255000000
//        // falseProbability = 0.03
//        bloomFilter.tryInit(255000000L, 0.03);
//        bloomFilter.add(new SomeObject("field1Value", "field2Value"));
//        bloomFilter.add(new SomeObject("field5Value", "field8Value"));
//        bloomFilter.contains(new SomeObject("field1Value", "field8Value"));


        RLiveObjectService liveObjectService = client.getLiveObjectService();
        Customer c1 = new Customer();
        c1.setId("11");
        c1.setAddress("武汉市");
        c1.setName("toggle");
        c1.setPhone("123213213213213");
        Customer c2 = new Customer();
        c2.setId("12");
        c2.setAddress("北京市");
        c2.setName("beijing");
        c2.setPhone("121399999");

        List<Customer> persist = liveObjectService.persist(c1, c2);
        for (Customer customer : persist) {
            System.out.println(customer.getId());
        }
    }

    public static void main04(String[] args) throws Exception {
        RedissonClient redisson = redissonClientSimple();

        RExecutorService executorService = redisson.getExecutorService("myExecutor");
        /**
         * lambda的序列化：转换为含有序列化的接口，在你的lambda所处的环境在序列化环境中的时候需要进行lambda序列化
         *
         */
        Future future = executorService.submit((Callable & Serializable) () -> 1L);
        System.out.println(future.get());
    }

}
