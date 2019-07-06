package com.uplooking.javassit;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class MyJavassist implements ClassFileTransformer {

    /**
     * 1.实现premain方法（步骤二）
     * 2.在MANIFEST.MF文件中有Premain-Class（maven可在pom文件中指定，普通java工程可以自己创建该文件）
     */

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        //1.创建classPool
        ClassPool classPool = ClassPool.getDefault();
        try {
            //2.获取指定的类的class对象
            CtClass ctClass = classPool.get("com.uplooking.Persion");
            //3.设置ctClass类的父类
            ctClass.setSuperclass(classPool.get("com.uplooking.Animal"));
            //4.保存（调用 writeFile() 后，这项修改会被写入原始类文件）
            ctClass.writeFile();
            //如果一个 CtClass 对象通过 writeFile(), toClass(), toBytecode() 被转换成一个类文件，此 CtClass 对象会被冻结起来，不允许再修改。因为一个类只能被 JVM 加载一次。
            //但是，一个冷冻的 CtClass 也可以被解冻(defrost)
            ctClass.defrost();
            //ClassPool.doPruning 被设置为 true
            ctClass.stopPruning(true);
            return null;
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (CannotCompileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
