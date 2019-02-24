package com.uplooking.configuration;

import com.uplooking.MyCondition;
import com.uplooking.factory.MyFactoryBean;
import com.uplooking.importselector.MyImportBeanDefinitionRegistrar;
import com.uplooking.importselector.MyImportSelector;
import com.uplooking.pojo.Color;
import com.uplooking.pojo.Dog;
import com.uplooking.pojo.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;

@Configuration
//@ComponentSan是一个复合组件，可以重复使用
/*@ComponentScans(value = {
        @ComponentScan(value = "com.uplooking",
                excludeFilters = {@ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})}),
        @ComponentScan(value = "com.uplooking",
                includeFilters = {
                        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class),
                        //定制自定义规则
                        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
                }, useDefaultFilters = false)

})*/
/**
 * @Import：将类添加到IoC容器中：id为全类名
 *
 */
@Import({Color.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
//读取外部配置文件中的k/v保存到环境变量中
@PropertySource({"classpath:/application.yml"})
public class initConfiguration {

    /**
     * 1.包扫描：扫描的是标记了注解的@Controller @Component @Repository
     * 2.bean只是产生了id,并没有注入到容器中
     * 3.包扫描 + 注解标识
     * 4.xml中的扫描指定的是包路径下 + 实例化Bean
     * 5.SpringBoot中配置类：xml+注解版
     */
    @Autowired
    private Color color;

    @Scope("singleton") //默认单实例的时候IoC启动的时候，会自动添加到容器中
    @Lazy   //Ioc容器启动的时候，不会创建Bean，而是在获取的时候创建对象
    //@Scope("prototype")//多实例的时候IoC启动的时候不会添加到容器中，而是在使用的时候添加，
    // 并且每次调用都会创建对象调用方法
    @Bean //@Bean 只是产生了bean的id 但是并没有注入到容器中 1.包扫描 2.主动注入
    @Primary //
    public Person getPerson() {
        Person person = new Person();
        person.setName("张三");
        System.out.println("1233");
        return person;
    }

    @Conditional({MyCondition.class})//当符合MyCondition为true，才会添加到容器中
    @Bean
    public Dog dog() {
        return new Dog();
    }

    @Bean
    @Profile("test")
    public MyFactoryBean myFactoryBean() {
        return new MyFactoryBean();
    }


    @Bean
    public MyBeanDefinitionRegistryPostProcessor MyBeanDefinitionRegistryPostProcessor() {
        return new MyBeanDefinitionRegistryPostProcessor();
    }
}
