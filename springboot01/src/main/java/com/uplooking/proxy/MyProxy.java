package com.uplooking.proxy;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MyProxy {

    //还可以写注解的方式
    @Pointcut("execution(* com.uplooking.*(..))")
    private void pointcut() {
    }

    @Before("pointcut()")
    public void before(JoinPoint joinpoint) {
        System.out.println("前置通知" + joinpoint.getSignature());
    }

    @After("pointcut()")
    public void after(JoinPoint joinPoint) {
        System.out.println("后置通知" + joinPoint.getSignature());
    }

    @AfterReturning(value = "pointcut()", returning = "result")//这里的类型要匹配
    public Object afterReturning(JoinPoint joinPoint, Object result) {
        System.out.println("后置有返回值" + joinPoint.getSignature() + "->" + result);
        return result;
    }

    @AfterThrowing(value = "pointcut()", throwing = "throwable")//这里的类型要匹配
    public void throwing(JoinPoint joinPoint, Throwable throwable) {
        System.out.println("后置异常通知" + joinPoint.getSignature() + "->" + throwable.getMessage());
    }

    @Around("pointcut()")
    public Object around(JoinPoint joinPoint) throws Throwable {
        ProceedingJoinPoint proceedingJoinPoint = (ProceedingJoinPoint) joinPoint;
        System.out.println("前置通知");
        Object obj = proceedingJoinPoint.proceed();
        System.out.println("后置通知");
        return obj;
    }
}
