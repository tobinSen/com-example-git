package demo;

import com.example.spring.redis.RedisMqApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisMqApplication.class)
public class RedisSetDemo {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 1、add(K key, V... values)
     * 2、members(K key)  返回set中所有元素
     * 3、size(K key)
     * 4、randomMember(K key)
     * 5、randomMembers(K key, long count) 指定获取几个值
     * 6、isMember(K key, Object o)
     * 7、move(K key, V value, K destKey) 移动
     * 8、pop(K key)  删除一个
     * 9、remove(K key, Object... values)  删除多个value
     * 10、scan(K key, ScanOptions options)
     * 11、difference(K key, Collection<K> otherKeys)
     * 12、difference(K key, K otherKey)
     * 13、differenceAndStore(K key, K otherKey, K destKey)
     * 14、differenceAndStore(K key, Collection<K> otherKeys, K destKey)
     * 15、distinctRandomMembers(K key, long count)
     * 16、intersect(K key, K otherKey)
     * 17、intersect(K key, Collection<K> otherKeys)
     * 18、intersectAndStore(K key, K otherKey, K destKey)
     * 19、intersectAndStore(K key, Collection<K> otherKeys, K destKey)
     * 20、union(K key, K otherKey)
     * 21、union(K key, Collection<K> otherKeys)
     * 22、unionAndStore(K key, K otherKey, K destKey)
     * 23、unionAndStore(K key, Collection<K> otherKeys, K destKey)
     */

    @Test
    public void setDemo() {
        //System.out.println(redisTemplate.opsForSet().add("set1", "set1", "set2", "set3", "set3", "set4"));
        //System.out.println(redisTemplate.opsForSet().members("set1"));
        //System.out.println(redisTemplate.opsForSet().pop("set1"));
//        ScanOptions scanOptions = ScanOptions.scanOptions().match("*1").build();
//        System.out.println(redisTemplate.opsForSet().scan("set1", ScanOptions.NONE));
//        System.out.println(redisTemplate.opsForSet().scan("set1", scanOptions));
    }
}
