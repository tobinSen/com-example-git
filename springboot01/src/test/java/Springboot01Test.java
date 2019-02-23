import com.uplooking.Springboot01Application;
import com.uplooking.pojo.Dog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

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
        System.out.println(dog.toString());
        //Dog dog = applicationContext.getBean(Dog.class);
        //System.out.println(dog.toString());
        //Person person = (Person) objectProvider.getIfUnique();
        //System.out.println(person.toString());
    }
}
