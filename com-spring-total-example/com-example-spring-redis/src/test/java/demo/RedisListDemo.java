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
public class RedisListDemo {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 1、leftPush(K key, V value)   从左边插入
     * 2、index(K key, long index)   获取key-->list中指定下标的值
     * 3、range(K key, long start, long end)  获取指定key中的list中，指定范围【0，-1】获取全部
     * 4、leftPush(K key, V pivot, V value)
     * 5、leftPushAll(K key, V... values)
     * 6、leftPushAll(K key, Collection<V> values)
     * 7、leftPushIfPresent(K key, V value)
     * 8、rightPush(K key, V value)
     * 9、rightPush(K key, V pivot, V value)
     * 10、rightPushAll(K key, V... values)
     * 11、rightPushAll(K key, Collection<V> values)
     * 12、rightPushIfPresent(K key, V value)
     * 13、size(K key)
     * 14、leftPop(K key)  删除从左边第一个元素
     * 15、leftPop(K key, long timeout, TimeUnit unit)
     * 16、rightPop(K key)     poll移除 删除从右边第一个元素
     * 17、rightPop(K key, long timeout, TimeUnit unit)
     * 18、rightPopAndLeftPush(K sourceKey, K destinationKey)  一个集合移除元素，添加到另一个集合中
     * 19、rightPopAndLeftPush(K sourceKey, K destinationKey, long timeout, TimeUnit unit)
     * 20、set(K key, long index, V value)  指定索引位置添加元素
     * 21、remove(K key, long count, Object value)
     * count> 0：删除等于从左到右移动的值的第一个元素；
     * count< 0：删除等于从右到左移动的值的第一个元素；
     * count = 0：删除等于value的所有元素
     * 22、trim(K key, long start, long end) 截取指定长度值，在保存到原来的集合中
     */
    @Test
    public void listDemo() {
        //System.out.println(redisTemplate.boundValueOps("tongjian").get());
        //System.out.println(redisTemplate.opsForList().leftPush("list1", "list2"));
        //System.out.println(redisTemplate.opsForList().index("list1", 0));
        //System.out.println(redisTemplate.opsForList().range("list1", 0, 1));
        //redisTemplate.opsForList().leftPush("list1", "a", "list2");
        //System.out.println(redisTemplate.opsForList().leftPushAll("list1", "list3", "list4", "list5"));
        //System.out.println(redisTemplate.opsForList().leftPushIfPresent("list1", "9999"));
        //System.out.println(redisTemplate.opsForList().leftPushIfPresent("lt1", "9999"));
        //System.out.println(redisTemplate.opsForList().leftPop("list1"));
        //System.out.println(redisTemplate.opsForList().rightPopAndLeftPush("list1", "00000"));
        //redisTemplate.opsForList().set("list1", 0, "787878");
        //System.out.println(redisTemplate.opsForList().remove("list1", 0, "list2"));
        redisTemplate.opsForList().trim("list1", 0, 1);

    }

}
