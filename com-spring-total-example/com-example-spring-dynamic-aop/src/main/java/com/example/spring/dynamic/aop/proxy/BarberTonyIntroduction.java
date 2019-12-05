package com.example.spring.dynamic.aop.proxy;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DelegatingIntroductionInterceptor;

/**
 * 引介增强为目标类创建新的方法和属性，所以引介增强的连接点是类级别的，而非方法级别的
 */
public class BarberTonyIntroduction {

    public static void main(String[] args) {
        ProxyFactory proxyFactory = new ProxyFactory();
        // 必须添加引介增强需要实现的接口
        proxyFactory.addInterface(MonitorStatus.class);
        // 引介增强的目标类
        proxyFactory.setTarget(new Service());
        // 引介增强的类
        proxyFactory.addAdvice(new ServiceMonitor());
        // 由于引介增强一定要通过创建子类来生成代理，所以必须需要强制使用CGLib
        proxyFactory.setProxyTargetClass(true);

        Service serviceProxy = (Service) proxyFactory.getProxy();
        // 虽然Service并没有直接实现MonitorStatus接口，但是其代理类动态添加了该接口
        ((MonitorStatus) serviceProxy).setMonitorStatus(true);
        serviceProxy.doSomething();
    }

    static class ServiceMonitor extends DelegatingIntroductionInterceptor implements MonitorStatus {
        // 因为这个控制状态使代理类变成了非线程安全的实例，需要每一个线程有单独的状态
        ThreadLocal<Boolean> status = new ThreadLocal<>();

        // 覆盖率父类的invoke方法用于增强代码
        public Object invoke(MethodInvocation mi) throws Throwable {
            // 需要判断是否是需要增强的方法
            if ("doSomething".equals(mi.getMethod().getName()) && status.get() != null && status.get()) {
                System.out.println("method enhance");
            }
            // 直接调用super.invoke(mi)来实现调用原来方法
            return super.invoke(mi);
        }

        public void setMonitorStatus(boolean status) {
            this.status.set(status);
        }
    }

    // 为目标类引介增强的接口
    interface MonitorStatus {
        void setMonitorStatus(boolean status);
    }

    // 需要增强的目标类
    static class Service {
        public void doSomething() {
            System.out.println("Service do something!");
        }
    }
}