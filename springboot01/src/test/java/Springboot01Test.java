import com.uplooking.Springboot01Application;
import com.uplooking.pojo.Dog;
import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Test
    public void test1() {
        System.out.println(dog.toString());
        Dog dog = applicationContext.getBean(Dog.class);
        System.out.println(dog.toString());
    }
}
