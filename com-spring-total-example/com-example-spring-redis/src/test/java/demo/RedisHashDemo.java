package demo;

import com.example.spring.redis.RedisMqApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 命令行查询：
 * get
 * lrange
 * smembers
 * zrange
 * hkeys
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisMqApplication.class)
public class RedisHashDemo {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 1、put(H key, HK hashKey, HV value)
     * 2、values(H key)
     * 3、entries(H key)
     * 4、get(H key, Object hashKey)
     * 5、hasKey(H key, Object hashKey)
     * 6、keys(H key)  获取变量中的键。
     * 7、size(H key)
     * 8、increment(H key, HK hashKey, double delta)
     * 9、increment(H key, HK hashKey, long delta)
     * 10、multiGet(H key, Collection<HK> hashKeys)
     * 11、putAll(H key, Map<? extends HK,? extends HV> m)
     * 12、putIfAbsent(H key, HK hashKey, HV value)
     * 13、scan(H key, ScanOptions options)
     * 14、delete(H key, Object... hashKeys)
     */
    @Test
    public void hashDemo() {
        //redisTemplate.execute(new DefaultRedisScript<>(), )
    }
}
