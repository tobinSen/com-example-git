package com.example.spring.redis.shardingsentinel;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.util.Hashing;

import java.util.*;

/**
 * Jedis包中有个很恶心的问题，那就是包里面有支持分片的ShardeJedis客户端类，也有支持哨兵的池类JedisSentinelPool，
 * 就是没有既支持分片又支持哨兵的池类，所以必须自己自定义一个ShardedJedisSentinelPool
 *
 * JedisShardInfo   -->  JedisSentinelPool
 */
public class ShardedJedisSentinelPool {

    // 一个MasterName --> List sentinels
    private Map<String, JedisSentinelPool> poolMap = new HashMap<>();
    private Hashing algo = Hashing.MURMUR_HASH;
    private TreeMap<Long, JedisShardInfo> nodes;

    /**
     * @param hostInfo  master主机信息
     * @param sentinels 哨兵信息
     */
    public ShardedJedisSentinelPool(Set<HostAndPort> hostInfo, Set<String> sentinels) {
        List<JedisShardInfo> list = new ArrayList<>();
        for (HostAndPort iter : hostInfo) {
            JedisShardInfo info = new JedisShardInfo(iter.getHost(), iter.getPort());
            list.add(info);
        }
        // 物理redis节点进行存储
        initialize(list);

        for (HostAndPort iter : hostInfo) {
            String masterName = "master-" + iter.getHost() + ":" + iter.getPort();
            JedisSentinelPool sentinelPool = new JedisSentinelPool(masterName, sentinels); // 将master和sentinels进行绑定
            poolMap.put(masterName, sentinelPool);
        }
    }

    public Jedis getResource(String key) {
        // 1. 根据key获取哪个node
        JedisShardInfo shardInfo = getShardInfo(key.getBytes());
        String masterName = "master-" + shardInfo.getHost() + ":" + shardInfo.getPort();
        JedisSentinelPool sentinuelPool = poolMap.get(masterName); //
        Jedis sentinelPool = sentinuelPool.getResource(); // 监听的jedis
        return sentinelPool;
    }

    private void initialize(List<JedisShardInfo> shards) {
        nodes = new TreeMap<>();

        // 虚拟节点一致性hash环存储
        for (int i = 0; i != shards.size(); ++i) {
            final JedisShardInfo shardInfo = shards.get(i);
            if (shardInfo.getName() == null) for (int n = 0; n < 160 * shardInfo.getWeight(); n++) {
                nodes.put(this.algo.hash("SHARD-" + i + "-NODE-" + n), shardInfo);
            }
            else for (int n = 0; n < 160 * shardInfo.getWeight(); n++) {
                nodes.put(this.algo.hash(shardInfo.getName() + "*" + shardInfo.getWeight() + n), shardInfo);
            }
        }
    }

    public JedisShardInfo getShardInfo(byte[] key) {
        SortedMap<Long, JedisShardInfo> tail = nodes.tailMap(algo.hash(key));
        if (tail.isEmpty()) {
            return nodes.get(nodes.firstKey());
        }
        return tail.get(tail.firstKey());
    }

    public void close() {
        for (Map.Entry<String, JedisSentinelPool> iter : poolMap.entrySet()) {
            iter.getValue().destroy();
        }
    }
}
