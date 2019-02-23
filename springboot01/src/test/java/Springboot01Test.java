import com.uplooking.Springboot01Application;
import com.uplooking.pojo.Dog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Springboot01Application.class)
//@WebAppConfiguration //获取webapp目录下的资源
public class Springboot01Test {

    @Autowired
    private Dog dog;

    @Test
    public void test1() {
        System.out.println(dog.toString());
    }
}
