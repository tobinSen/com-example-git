package com.example.spring.dynamic.aop.retry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.policy.TimeoutRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

@Service
public class PayService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private final int totalNum = 100000;


    //value值表示当哪些异常的时候触发重试，maxAttempts表示最大重试次数默认为3，delay表示重试的延迟时间，multiplier表示上一次延时时间是这一次的倍数。
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 2000, multiplier = 1.5))
    public int minGoodsnum(int num) throws Exception {
        logger.info("minGoodsnum开始" + LocalTime.now());
        if (num <= 0) {
            throw new Exception("数量不对"); //这里对应@Recover
        }
        logger.info("minGoodsnum执行结束");
        return totalNum - num;
    }

    @Recover
    public int recover(Exception e) {
        logger.warn("减库存失败！！！");
        //记日志到数据库
        return totalNum;
    }

    public static void main(String[] args) throws Exception {
        RetryTemplate template = new RetryTemplate();

        TimeoutRetryPolicy policy = new TimeoutRetryPolicy();
        policy.setTimeout(2000L);


        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(5);

        //template.setRetryPolicy(policy);
        template.setRetryPolicy(retryPolicy);

        String result = template.execute(context -> {

            //这里第一次也是重试
            System.err.println("TestAll.main()1");
            TimeUnit.SECONDS.sleep(1L);
            throw new IllegalArgumentException();
        }, context -> {
            //重试全部完成后还是失败会回调此方法
            System.err.println("TestAll.main()2");
            return "world";
        });
        System.err.println("result:" + result);

    }
}