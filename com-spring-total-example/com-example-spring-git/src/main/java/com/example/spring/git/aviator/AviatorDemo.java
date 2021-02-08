package com.example.spring.git.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorDouble;
import com.googlecode.aviator.runtime.type.AviatorObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

public class AviatorDemo {
    public static void main01(String[] args) {
        System.out.println("-----------------------------------------------------------------");
        System.out.println("算术表达式【1+1】： " + AviatorEvaluator.execute("1+1"));
        System.out.println("逻辑表达式【1==1】： " + AviatorEvaluator.execute("1==1"));
        System.out.println("三元表达式【1==1 ? '对' : '错'】： " + AviatorEvaluator.execute("1==1 ? '对' : '错'"));
        System.out.println("函数调用【6的3次方】： " + AviatorEvaluator.execute("math.pow(6,3)"));
        System.out.println("-----------------------------------------------------------------");
    }

    public static void main02(String[] args) {
        // 输出的是6.8
        System.out.println(AviatorEvaluator.execute("1 + 2.8 + 3"));

        String name = "鹿骁俸";
        Map<String, Object> env = new HashMap<>();
        env.put("name", name);
        // 输出的是你的名字是：鹿骁俸
        System.out.println(AviatorEvaluator.execute("'你的名字是：' + name", env));
        // Aviator 2.2 开始新增加一个exec方法, 可以更方便地传入变量并执行, 而不需要构造env这个map了
        System.out.println(AviatorEvaluator.exec("'你的名字是：' + name", name));

        env.put("a", 5);
        env.put("b", 4);
        // 输出的是6.333333333333333
        System.out.println(AviatorEvaluator.execute("a + b / 3.0", env));
        // 推荐的使用方式
        System.out.println(AviatorEvaluator.compile("a + b / 3.0").execute(env));
    }

    public static void main03(String[] args) {
        // 输出的是a"b
        System.out.println(AviatorEvaluator.execute("'a\"b'"));
        // 输出的是a'b
        System.out.println(AviatorEvaluator.execute("\"a\'b\""));
        // 输出的是hello 8
        System.out.println(AviatorEvaluator.execute("'hello ' + 8"));
        // 输出的是hello null
        System.out.println(AviatorEvaluator.execute("'hello ' + unknow"));
    }

    public static void main04(String[] args) {
        // 输出的是5
        System.out.println(AviatorEvaluator.execute("string.length('hello')"));
        // 输出的是true
        System.out.println(AviatorEvaluator.execute("string.contains(\"test\", string.substring('sword', 0, 1))"));

        // 注册函数使用addFunction，移除函数使用removeFunction
        AviatorEvaluator.addFunction(new AddFunction());
        // 输出的是3.0
        System.out.println(AviatorEvaluator.execute("add(1, 2)"));
        // 输出的是103.0
        System.out.println(AviatorEvaluator.execute("add(add(1, 2), 100)"));
    }

    static class AddFunction extends AbstractFunction {
        @Override
        public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
            Number left = FunctionUtils.getNumberValue(arg1, env);
            Number right = FunctionUtils.getNumberValue(arg2, env);
            return new AviatorDouble(left.doubleValue() + right.doubleValue());
        }

        public String getName() {
            return "add";
        }
    }

    public static void main05(String[] args) {
        String expression = "a - (b - c) > 100";
        // 编译表达式
        Expression compiledExp = AviatorEvaluator.compile(expression);
        Map<String, Object> env = new HashMap<>();
        env.put("a", 100.3);
        env.put("b", 45);
        env.put("c", 27.68);
        // 执行表达式
        Boolean result = (Boolean) compiledExp.execute(env);
        // 输出的是false
        System.out.println(result);
    }

    public static void main06(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        Map<String, Object> env = new HashMap<>();
        env.put("list", list);
        // hello world
        System.out.println(AviatorEvaluator.execute("list[0] + list[1]", env));

        int[] array = new int[3];
        array[0] = 0;
        array[1] = 1;
        array[2] = 3;
        env.put("array", array);
        // array[0] + array[1] + array[2] = 4
        System.out.println(AviatorEvaluator.execute("'array[0] + array[1] + array[2] = ' + (array[0] + array[1] + array[2])", env));

        Map<String, Date> map = new HashMap<>();
        map.put("date", new Date());
        env.put("map", map);
        // today is Sat Dec 22 12:59:15 CST 2018
        System.out.println(AviatorEvaluator.execute("'today is ' + map.date", env));


        ArrayList<Integer> numList = new ArrayList<>();
        numList.add(3);
        numList.add(20);
        numList.add(10);
        numList.add(1);
        env.put("numList", numList);
        /**
         *     ●求长度：count(list)
         *     ●求和：reduce(list,+,0)，reduce函数接收三个参数，第一个是seq，第二个是聚合的函数，如+等，第三个是聚合的初始值。
         *     ●过滤：filter(list,seq.gt(9))，过滤出list中所有大于9的元素并返回集合。seq.gt函数用于生成一个谓词，表示大于某个值。
         *     ●判断元素在不在集合里：include(list,10)
         *     ●排序：sort(list)
         *     ●遍历整个集合：map(list,println)，map接受的第二个函数将作用于集合中的每个元素，这里简单地调用println打印每个元素。
         */
        // 4
        System.out.println(AviatorEvaluator.execute("count(numList)", env));
        // 34
        System.out.println(AviatorEvaluator.execute("reduce(numList, + ,0)", env));
        // [20, 10]
        System.out.println(AviatorEvaluator.execute("filter(numList,seq.gt(9))", env));
        // [10]
        System.out.println(AviatorEvaluator.execute("filter(numList, seq.and(seq.ge(5), seq.lt(15)))", env));
        // true
        System.out.println(AviatorEvaluator.execute("include(numList,10)", env));
        // [1, 3, 10, 20]
        System.out.println(AviatorEvaluator.execute("sort(numList)", env));
        // 遍历输出
        AviatorEvaluator.execute("map(numList,println)", env);
    }

    public static void main(String[] args) {
        // 输出的是yes
        System.out.println(AviatorEvaluator.exec("a > 0 ? 'yes':'no'", 1));

        Map<String, Object> env = new HashMap<>();
        env.put("email", "killme2008@gmail.com");
        String username = (String) AviatorEvaluator.execute("email=~/([\\w0-8]+)@\\w+[\\.\\w+]+/ ? $1 : 'unknow' ", env);
        // 输出的是killme2008
        System.out.println(username);


        //===========================Aviator 规定，任何对象都比nil大除了nil本身。用户传入的变量如果为null，将自动以nil替代===============================================
        //nil是 Aviator 内置的常量，类似 java 中的null，表示空的值。
        //nil跟null不同的在于，在 java 中null只能使用==、!=比较运算符，而nil还可以使用>、>=、<、<=等比较运算符
        // 输出的是true
        System.out.println(AviatorEvaluator.execute("nil == nil"));
        // 输出的是true
        System.out.println(AviatorEvaluator.execute("3 > nil"));
        // 输出的是true
        System.out.println(AviatorEvaluator.execute("true != nil"));
        // 输出的是true
        System.out.println(AviatorEvaluator.execute("'' > nil"));
        // 输出的是true
        System.out.println(AviatorEvaluator.execute("a == nil"));

        //===========================Aviator 规定，任何对象都比nil大除了nil本身。用户传入的变量如果为null，将自动以nil替代===============================================


        final Date date = new Date();
        String dateStr = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS").format(date);
        env.put("date", date);
        env.put("dateStr", dateStr);
        // 输出的是true
        System.out.println(AviatorEvaluator.execute("date == dateStr", env));
        // 输出的是true
        System.out.println(AviatorEvaluator.execute("date > '2010-01-01 00:00:00:00'", env));
        // 输出的是false
        System.out.println(AviatorEvaluator.execute("date > '2020-01-01 00:00:00:00'", env));
        // 输出的是true
        System.out.println(AviatorEvaluator.execute("date == date", env));

        //==========================================================================


        // 输出的是199999999999999999999999999999998
        System.out.println(AviatorEvaluator.exec("99999999999999999999999999999999 + 99999999999999999999999999999999"));

        Object rt = AviatorEvaluator.exec("9223372036854775807100.356M * 2");
        // 输出的是18446744073709551614200.712 class java.math.BigDecimal
        System.out.println(rt + " " + rt.getClass());

        rt = AviatorEvaluator.exec("92233720368547758074 + 1000");
        // 输出的是92233720368547759074 class java.math.BigInteger
        System.out.println(rt + " " + rt.getClass());

        BigInteger a = new BigInteger(String.valueOf(Long.MAX_VALUE) + String.valueOf(Long.MAX_VALUE));
        BigDecimal b = new BigDecimal("3.2");
        BigDecimal c = new BigDecimal("9999.99999");
        rt = AviatorEvaluator.exec("a + 10000000000000000000", a);
        // 输出的是92233720368547758089223372036854775807 class java.math.BigInteger
        System.out.println(rt + " " + rt.getClass());
        rt = AviatorEvaluator.exec("b + c * 2", b, c);
        // 输出的是20003.19998 class java.math.BigDecimal
        System.out.println(rt + " " + rt.getClass());
        rt = AviatorEvaluator.exec("a * b / c", a, b, c);
        // 输出的是2.951479054745007313280155218459508E+34 class java.math.BigDecimal
        System.out.println(rt + " " + rt.getClass());
//    类型转换和提升：
//      当big int或者decimal和其他类型的数字做运算的时候，按照long < big int < decimal < double的规则做提升，也就是说运算的数字如果类型不一致，结果的类型为两者之间更“高”的类型。例如：
//        ●1 + 3N，结果为big int的4N
//        ●1 + 3.1M，结果为decimal的4.1M
//        ●1N + 3.1M，结果为decimal的 4.1M
//        ●1.0 + 3N，结果为double的4.0
//        ●1.0 + 3.1M，结果为double的4.1
//
//    decimal 的计算精度：
//      Java 的java.math.BigDecimal通过java.math.MathContext支持特定精度的计算，任何涉及到金额的计算都应该使用decimal类型。
//      默认 Aviator 的计算精度为MathContext.DECIMAL128，你可以自定义精度，通过：
//
//        AviatorEvaluator.setMathContext(MathContext.DECIMAL64);
//      即可设置，更多关于decimal的精度问题请看java.math.BigDecimal的 javadoc 文档。
//
//  10.变量的语法糖：
//    Aviator 有个方便用户使用变量的语法糖，当你要访问变量a中的某个属性b，那么你可以通过a.b访问到，更进一步，可以通过a.b.c访问变量a的b属性中的c属性值，推广开来也就是说 Aviator 可以将变量声明为嵌套访问的形式。
//    AviatorTest 类符合JavaBean规范，并且是 public 的，就可以使用语法糖：




    }
}
