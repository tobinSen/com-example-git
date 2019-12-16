package com.example.spring.log4j2;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

@Service("pushService")
public class PushServiceImpl {

    //longPoll请求
    private DeferredResult deferredResult ; //前台发送的请求

    public DeferredResult<String> getDeferredResult(){
        deferredResult = new DeferredResult() ;
        return deferredResult ;
    }
    @Scheduled(fixedRate = 3000) //每隔3秒从请求中扫描一次请求，扫描到就开始推送
    public void refresh(){
        if(null!=deferredResult){
            //setResult了，才会返回响应信息，不然一致堵塞
            deferredResult.setResult(new Long(System.currentTimeMillis()).toString()) ;
        }
    }
}
