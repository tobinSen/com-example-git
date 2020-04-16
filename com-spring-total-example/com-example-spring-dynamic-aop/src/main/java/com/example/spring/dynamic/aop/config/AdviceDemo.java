package com.example.spring.dynamic.aop.config;

/**
 * 总结：5，7，5,4,535
 *                                   Advice
 *
 *       |                             |                        |
 *  BeforeAdvice                  AfterAdvice                Interceptor
 *      |                    |                |                |
 *  MethodBeforeAdvice  AfterReturningAdvice  ThrowsAdvice  MethodInterceptor (环绕通知)
 *                                                            |
 *                                                         IntroductionInterceptor（引介通知）
 *
 *  ===========================================================================================
 *                            Pointcut  -> ClassFilter(定位特定的类) | MethodMatcher(定位特定的方法)
 *                               |
 *
 *  StaticMethodMatcher                             DynamicMethodMatcher
 *          |                                               |
 * StaticMethodMatcherPointcut                     DynamicMethodMatcherPointcut    AnnotationMatchingPointcut   ExpressionPointcut   ControlFlowPointcut  ComposablePointcut
 *           |
 * NameMatchMethodPointcut | AbstractRegexpMethodPointcut
 *
 *================================================================================================
 *                                   Advisor
 *                                      |
 *                   PointcutAdvisor        IntroductionAdvisor
 *
 *   DefaultPointcutAdvisor                    DefaultIntroductionAdvisor
 *   NameMatchMethodPointcutAdvisor            DeclareParentsAdvisor
 *   RegexpMethodPointcutAdvisor
 *   StaticMethodMatcherPointcutAdvisor
 *   AspectJExpressionPointcutAdvisor 动态性
 *   AspectJPointcutAdvisor
 *  注意：
 *      有些切面拥有切点的功能
 *=================================================================================================
 * 注册切面： （5,3点）
 * ProxyFactoryBean -> 接口 + 切面(通知,指定运用到了method)
 * BeanNameAutoProxyCreator -> 接口实现bean + 切面(通知,指定运用到了method)
 * AspectJExpressionPointcutAdvisor -> DefaultAdvisorAutoProxyCreator + 切面(通知，表达式)
 * AbstractBeanFactoryPointcutAdvisor
 * AbstractPointcutAdvisor
 *
 *
 * DefaultBeanFactoryPointcutAdvisor
 *
 * 步骤：
 *          NameMatchMethodPointcut cacheablePointcut = new NameMatchMethodPointcut();
 *          cacheablePointcut.addMethodName(methodName);
 *
 *          QueryResultCacheInterceptor cacheInterceptor = new QueryResultCacheInterceptor(objClazzHavingAnnotatedMethods);
 *          Advisor cacheableAdvisor = new DefaultPointcutAdvisor(cacheablePointcut, cacheInterceptor);
 *
 *          proxyFactory.addAdvisor(cacheableAdvisor);
            proxyFactory.setTarget(target);
            T proxy = (T) proxyFactory.getProxy();
 *
 *
 *重复注解Repeatable
 *
 *
 *
 *
 *
 */
public class AdviceDemo {
}
