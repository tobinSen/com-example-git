package com.example.spring.dynamic.aop.proxy;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.support.DynamicMethodMatcherPointcut;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.Method;

public class DynamicMethodMatcherPointcutDemo {

    public static void main(String[] args) throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Waitress waitress = context.getBean("proxy", Waitress.class);

        waitress.sayHello("zzx");
        waitress.sayHello("zzx");

        waitress.sayGoodbye("lucas");
        waitress.sayGoodbye("lucas");

    }

    /**
     * 目标类
     */
    class Waitress {
        public void sayHello(String name) {
            System.out.println("hello " + name + "!");
        }

        public void sayGoodbye(String name) {
            System.out.println("goodbye " + name + "!");
        }
    }

    class WaitressPointcut extends DynamicMethodMatcherPointcut {
        // 对方法进行静态检查
        public boolean matches(Method method, Class<?> targetClass) {
            System.out.println("静态检查: class=" + targetClass.getSimpleName() + ", method=" + method.getName());
            return "sayHello".equals(method.getName());
        }

        // 对方法进行动态检查
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            System.out.println("动态检查: class=" + targetClass.getSimpleName() + ", method=" + method.getName());
            return true;
        }
    }

    /**
     * 定义增强，此处定义了一个方法前置增强
     */
    class WaitressAdvice implements MethodBeforeAdvice {
        @Override
        public void before(Method method, Object[] args, Object target) throws Throwable {
            System.out.println("Morning " + args[0] + "!");
        }
    }
}