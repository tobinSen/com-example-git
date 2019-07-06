package com.uplooking.javassit;

import javassist.*;

import java.io.ByteArrayInputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * class文件转换器
 */
public class PerfMonXformer implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        byte[] transformed = null;
        System.out.println("transforming   " + className);
        ClassPool pool = ClassPool.getDefault();
        CtClass cl = null;

        try {
            cl = pool.makeClass(new ByteArrayInputStream(classfileBuffer));
            if (cl.isInterface() == false) {//如果不是接口
                //获取方法声明的集合并做相应处理
                CtBehavior[] methods = cl.getDeclaredBehaviors();
                for (int i = 0; i < methods.length; i++) {
                    /**
                     * Modifier.isNative(methods[i].getModifiers())过滤本地方法,否则会报
                     * javassist.CannotCompileException: no method body  at javassist.CtBehavior.addLocalVariable()
                     * 报错原因如下
                     * 来自Stack Overflow网友解答
                     * Native methods cannot be instrumented because they have no bytecodes.
                     * However if native method prefix is supported ( Transformer.isNativeMethodPrefixSupported() )
                     * then you can use Transformer.setNativeMethodPrefix() to wrap a native method call inside a non-native call
                     * which can then be instrumented
                     */
                    if (methods[i].isEmpty() == false && !Modifier.isNative(methods[i].getModifiers())) {
                        doMethod(methods[i]);
                    }
                }
                //将修改后的CtClass对象转化为字节码
                transformed = cl.toBytecode();
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cl != null) {
                cl.detach();
            }
        }
        //返回修改后的字节码
        return transformed;
    }

    /**
     * 对方法做处理
     *
     * @param method
     */
    private void doMethod(CtBehavior method) throws CannotCompileException {
        //增加本地变量
        method.addLocalVariable("startTime", CtClass.longType);
        method.addLocalVariable("endTime", CtClass.longType);
        //在方法前加入
        method.insertBefore("startTime = System.nanoTime(); System.out.println(\"enter " + method.getName() + " time \" + startTime);");
        //在方法后加入
        method.insertAfter("endTime = System.nanoTime(); System.out.println(\"leave " + method.getName() + " time \" + endTime);");
        method.insertAfter(" System.out.println(\"time difference " + method.getName() + " \" +(endTime - startTime));");
    }

}
