package com.example.spring.dynamic.aop.spel;

import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SPELDemo {

    public static void main01(String[] args) {
        String expressionStr = "1 + 2";
        ExpressionParser parpser = new SpelExpressionParser(); //SpelExpressionParser是Spring内部对ExpressionParser的唯一最终实现类
        Expression exp = parpser.parseExpression(expressionStr); //把该表达式，解析成一个Expression对象：SpelExpression

        // 方式一：直接计算
        Object value = exp.getValue();
        System.out.println(value.toString()); //3
        // 若你在@Value中或者xml使用此表达式，请使用#{}包裹~~~~~~~~~~~~~~~~~
        System.out.println(parpser.parseExpression("T(System).getProperty('user.dir')").getValue()); //E:\work\remotegitcheckoutproject\myprojects\java\demo-war
        System.out.println(parpser.parseExpression("T(java.lang.Math).random() * 100.0").getValue()); //27.38227555400853

        // 方式二：定义环境变量，在环境内计算拿值
        // 环境变量可设置多个值：比如BeanFactoryResolver、PropertyAccessor、TypeLocator等~~~
        // 有环境变量，就有能力处理里面的占位符 ${}
        EvaluationContext context = new StandardEvaluationContext();
        System.out.println(exp.getValue(context)); //3
    }

    public static void main02(String[] args) {
        ExpressionParser parser = new SpelExpressionParser();

        Person person = new Person("fsx", 30);
        List<String> list = new ArrayList<String>() {{
            add("fsx");
            add("周杰伦");
        }};
        Map<String, Integer> map = new HashMap<String, Integer>() {{
            put("fsx", 18);
            put("周杰伦", 40);
        }};

        EvaluationContext ctx = new StandardEvaluationContext();
        //把list和map都放进环境变量里面去
        ctx.setVariable("myPerson", person);
        ctx.setVariable("myList", list);
        ctx.setVariable("myMap", map);
//        ((StandardEvaluationContext) ctx).setVariables();
        ((StandardEvaluationContext) ctx).setRootObject(person);


        //============================================
        System.out.println(parser.parseExpression("#myPerson").getValue(ctx)); //Person{name='fsx', age=30}
        System.out.println(parser.parseExpression("#myPerson.name").getValue(ctx)); //fsx
        // setVariable方式取值不能像root一样，前缀不可省略~~~~~
        System.out.println(parser.parseExpression("#name").getValue(ctx)); //null 显然找不到这个key就返回null呗~~~
        // 不写前缀默认去root找，找出一个null。再访问name属性那可不报错了吗
        System.out.println(parser.parseExpression("name").getValue(ctx)); // Property or field 'name' cannot be found on null


        System.out.println(parser.parseExpression("#myList").getValue(ctx)); // [fsx, 周杰伦]
        System.out.println(parser.parseExpression("#myList[1]").getValue(ctx)); // 周杰伦

        // 请注意对Map取值两者的区别：中文作为key必须用''包起来   当然['fsx']也是没有问题的
        System.out.println(parser.parseExpression("#myMap[fsx]").getValue(ctx)); //18
        System.out.println(parser.parseExpression("#myMap['周杰伦']").getValue(ctx)); //40

        // =========若采用#key引用的变量不存在，返回的是null，并不会报错哦==============
        System.out.println(parser.parseExpression("#map").getValue(ctx)); //null

        // 黑科技：SpEL内直接可以使用new方式创建实例  能创建数组、List  但不能创建普通的实例对象（难道是我还不知道）~~~~
        System.out.println(parser.parseExpression("new String[]{'java','spring'}").getValue()); //[Ljava.lang.String;@30b8a058
        System.out.println(parser.parseExpression("{'java','c语言','PHP'}").getValue()); //[java, c语言, PHP] 创建List
        System.out.println(parser.parseExpression("new Person()").getValue()); //A problem occurred whilst attempting to const
    }

    public static void main03(String[] args) {
        String expressionStr1 = "#person.name";
        String expressionStr2 = "#person.age";
        EvaluationContext ctx = new StandardEvaluationContext();
        Person person = new Person("fsx", 30);
        ctx.setVariable("person", person);
        ExpressionParser parser = new SpelExpressionParser();
        String name = (String) parser.parseExpression(expressionStr1).getValue(ctx);
        Integer age = (Integer) parser.parseExpression(expressionStr2).getValue(ctx);

        System.out.println("lock-key:" + name + "_" + age);
    }

    public static void main(String[] args) {
        String expression = "#person.name#person.age"; //复合key
        EvaluationContext ctx = new StandardEvaluationContext();
        Person person = new Person("fsx", 30);
        ctx.setVariable("person", person);
        ExpressionParser parser = new SpelExpressionParser();
        String[] split = expression.split("#");
        for (String express : split) {
            
        }
    }

    public static class Person {
        public String name;
        public Integer age;

        public Person(String name, Integer age) {
            this.name = name;
            this.age = age;
        }
    }
}
