import com.uplooking.Springboot01Application;
import com.uplooking.delay.Member;
import com.uplooking.pojo.Dog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Springboot01Application.class)
//@WebAppConfiguration //获取webapp目录下的资源
public class Springboot01Test {

    @Autowired
    private Dog dog;

    //【注意】 这个是容器启动后自行进行创建的
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ObjectProvider objectProvider;

    /**
     * 四种方式注入容器中：
     * 1、 xml
     * 2、注解 @Controller @Service @Repository
     * 3、配置类 @configuration @Bean
     * 4、容器自动运行时候创建的 ApplicationContext
     * 5、@Import()
     * 1、@Import(要导入到容器中的组件)，容器中就会自动注册这个组件 id默认全类名
     * 2、ImportSelector：返回需要导入的组件的全类名数据组
     * 3、ImportBeanDefinitionRegistrar:手动组成Bean到容器中
     * 6、使用FactoryBean(工厂Bean)
     * 1、默认获取的是工厂Bean调用getObject创建的对象
     * 2、要获取工厂Bean本身，我们需要给id前面加一个&
     * &myFactoryBean
     * <p>
     * 三种依赖注入的方式
     * 1、注解方式 @Autowired @Qualifier @Resource
     * 2、applicationContext.getBean(Person.class);
     * 3、ObjectProvider.getIfAvailable();
     * <p>
     * 注意：
     * 形参直接可以从容器中获取
     * dubbo引入会自动注入到spring容器中
     */

    @Test
    public void test1() {
        //System.out.println(dog.toString());
        //Dog dog = applicationContext.getBean(Dog.class);
        //System.out.println(dog.toString());
        //Person person = (Person) objectProvider.getIfUnique();
        //System.out.println(person.toString());
        long convert = TimeUnit.HOURS.convert(1, TimeUnit.MINUTES);
        System.out.println(convert);
    }

    @Test
    public void test2() throws Exception {
        DelayQueue<Member> queue = new DelayQueue<Member>();
        queue.add(new Member("张三", 3L, TimeUnit.SECONDS));
        queue.add(new Member("李四", 5L, TimeUnit.SECONDS));
        queue.add(new Member("王五", 5L, TimeUnit.SECONDS));
        while (!queue.isEmpty()) {
            //获取里面数据内容
            Delayed delayed = queue.poll();

            //延迟500毫秒
            TimeUnit.MILLISECONDS.sleep(500);


        }
    }

}
