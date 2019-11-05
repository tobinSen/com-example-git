package demo;

import com.example.spring.redis.RedisMqApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisMqApplication.class)
public class RedisStringDemo {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 1、set(K key, V value)
     * 2、get(Object key)  key不存在返回null
     * 3、append(K key, String value)：key存在，则在对应value追加，key不存在，就添加
     * 4、get(K key, long start, long end) 截取value的长度 【0，-1】截取全部(全包)
     * 5、getAndSet(K key, V value)  先获取oldValue，然后设置value,返回oldValue，如果不存在key,就返回null
     * 6、setBit(K key, long offset, boolean value)
     * 7、getBit(K key, long offset)
     * 8、size(K key)              value值的长度
     * 9、increment(K key, double delta)
     * 10、increment(K key, long delta)
     * 11、setIfAbsent(K key, V value)   不存在key才插入进去，返回true | false
     * 12、set(K key, V value, long timeout, TimeUnit unit)  设置过期日期
     * 13、set(K key, V value, long offset)  在指定位置用空格覆盖
     * 14、multiSet(Map<? extends K,? extends V> map)
     * 15、multiGet(Collection<K> keys)
     * 16、multiSetIfAbsent(Map<? extends K,? extends V> map)
     */
    @Test
    public void stringDemo() {
        //redisTemplate.opsForValue().set("tongjian", "tongjian");
        System.out.println(redisTemplate.opsForValue().get("jory"));
        //System.out.println(redisTemplate.opsForValue().append("tom", "1245007716@qq.com"));
//        System.out.println(redisTemplate.opsForValue().get("tongjian", 0, 2));
//        System.out.println(redisTemplate.opsForValue().get("tongjian", 0, -1));
        // System.out.println(redisTemplate.opsForValue().getAndSet("tongjian", "1040492411@qq.com"));
        //System.out.println(redisTemplate.opsForValue().getAndSet("por", "1040492411@qq.com"));
        //System.out.println(redisTemplate.opsForValue().setBit("tongjian", 1, true));
        //System.out.println(redisTemplate.opsForValue().size("tongjian"));
        //System.out.println(redisTemplate.opsForValue().increment("tom", 3));
        //System.out.println(redisTemplate.opsForValue().setIfAbsent("0000", "999999"));
        //redisTemplate.opsForValue().set("perter", "perter", 10, TimeUnit.SECONDS);
        //redisTemplate.opsForValue().set("jory", "jory", 2);
    }


}
