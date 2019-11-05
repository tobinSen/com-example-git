package demo;


import com.example.spring.redis.RedisMqApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisMqApplication.class)
public class RedisZsetDemo {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 1、add(K key, V value, double score)
     * 2、range(K key, long start, long end)
     * 3、rangeByLex(K key, RedisZSetCommands.Range range)  这是value的值
     * 4、rangeByLex(K key, RedisZSetCommands.Range range, RedisZSetCommands.Limit limit)
     * 5、add(K key, Set<ZSetOperations.TypedTuple<V>> tuples)
     * 6、rangeByScore(K key, double min, double max)
     * 7、rangeByScore(K key, double min, double max,long offset, long count)：根据设置的score获取区间值从给定【下标】和给定【长度】获取最终值。
     * 8、rangeWithScores(K key, long start, long end)
     * 9、rangeByScoreWithScores(K key, double min, double max) 获取RedisZSetCommands.Tuples的区间值通过分值
     * 10、rangeByScoreWithScores(K key, double min, double max, long offset, long count)
     * 11、count(K key, double min, double max)
     * 12、rank(K key, Object o) ：获取变量中元素的索引,下标开始位置为0
     * 13、scan(K key, ScanOptions options)
     * 14、score(K key, Object o)
     * 15、zCard(K key) ：zset中有多少元素
     * 16、incrementScore(K key, V value, double delta)
     * 17、reverseRange(K key, long start, long end) :排序了的
     * 18、reverseRangeByScore(K key, double min, double max)
     * 19、reverseRangeByScore(K key, double min, double max, long offset, long count)
     * 20、reverseRangeByScoreWithScores(K key, double min, double max)
     * 21、reverseRangeByScoreWithScores(K key, double min, double max, long offset, long count)
     * 22、reverseRangeWithScores(K key, long start, long end)
     * 23、reverseRank(K key, Object o)
     * 24、intersectAndStore(K key, K otherKey, K destKey)
     * 25、intersectAndStore(K key, Collection<K> otherKeys, K destKey)
     * 26、unionAndStore(K key, K otherKey, K destKey)
     * 28、remove(K key, Object... values)
     * 29、removeRangeByScore(K key, double min, double max)
     * 30、removeRange(K key, long start, long end)
     */
    @Test
    public void zsetDemo() {
        //System.out.println(redisTemplate.opsForZSet().add("zset1", "zset1", 10));
        RedisZSetCommands.Range range = new RedisZSetCommands.Range();
        range.gte("B");
        System.out.println(redisTemplate.opsForZSet().rangeByLex("zset1", range));
        //redisTemplate.opsForZSet().reverseRange(, , )
    }
}
