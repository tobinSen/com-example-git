package com.example.spring.jedis.test;

import com.google.common.base.Preconditions;
import com.google.common.hash.Funnel;
import com.google.common.hash.Hashing;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

public class RedisService {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 根据给定的布隆过滤器添加值
     */
    public <T> void addByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            redisTemplate.opsForValue().setBit(key, i, true);
        }
    }

    /**
     * 根据给定的布隆过滤器判断值是否存在
     */
    public <T> boolean includeByBloomFilter(BloomFilterHelper<T> bloomFilterHelper, String key, T value) {
        Preconditions.checkArgument(bloomFilterHelper != null, "bloomFilterHelper不能为空");
        int[] offset = bloomFilterHelper.murmurHashOffset(value);
        for (int i : offset) {
            if (!redisTemplate.opsForValue().getBit(key, i)) {
                return false;
            }
        }

        return true;
    }

    public class BloomFilterHelper<T> {

        private int numHashFunctions;

        private int bitSize;

        private Funnel<T> funnel;

        public BloomFilterHelper(Funnel<T> funnel, int expectedInsertions, double fpp) {
            Preconditions.checkArgument(funnel != null, "funnel不能为空");
            this.funnel = funnel;
            bitSize = optimalNumOfBits(expectedInsertions, fpp);
            numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, bitSize);
        }

        int[] murmurHashOffset(T value) {
            int[] offset = new int[numHashFunctions];

            long hash64 = Hashing.murmur3_128().hashObject(value, funnel).asLong();
            int hash1 = (int) hash64;
            int hash2 = (int) (hash64 >>> 32);
            for (int i = 1; i <= numHashFunctions; i++) {
                int nextHash = hash1 + i * hash2;
                if (nextHash < 0) {
                    nextHash = ~nextHash;
                }
                offset[i - 1] = nextHash % bitSize;
            }

            return offset;
        }

        /**
         * 计算bit数组长度
         */
        private int optimalNumOfBits(long n, double p) {
            if (p == 0) {
                p = Double.MIN_VALUE;
            }
            return (int) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
        }

        /**
         * 计算hash方法执行次数
         */
        private int optimalNumOfHashFunctions(long n, long m) {
            return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
        }
    }
}
