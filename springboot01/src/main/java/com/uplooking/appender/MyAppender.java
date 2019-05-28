//package com.uplooking.appender;
//
//
//import org.apache.log4j.Level;
//import org.apache.log4j.spi.LocationInfo;
//import org.apache.log4j.spi.LoggingEvent;
//import org.apache.log4j.spi.ThrowableInformation;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.stereotype.Component;
//
///**
// * 自定义输出源
// */
//@Component
//@ComponentScan
//public class MyAppender extends org.apache.log4j.AppenderSkeleton {
//
//
//    @Override
//    protected void append(LoggingEvent event) {
//        //日志输出的内容
//        String msgContent = event.getMessage().toString();
//        //日志等级
//        Level level = event.getLevel();
//        boolean equals = Level.INFO.equals(level);
//        //日志位置
//        LocationInfo locationInformation = event.getLocationInformation();
//        String className = locationInformation.getClassName();
//        String methodName = locationInformation.getMethodName();
//        //
//        String threadName = event.getThreadName();
//        long timeStamp = event.getTimeStamp();
//        ThrowableInformation throwableInformation = event.getThrowableInformation();
//        //最后就进行封装插入到es中
//    }
//
//    @Override
//    public void close() {
//
//    }
//
//    @Override
//    public boolean requiresLayout() {
//        return false;
//    }
//}
