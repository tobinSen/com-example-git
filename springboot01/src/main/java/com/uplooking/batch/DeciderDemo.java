package com.uplooking.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class DeciderDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    //任务的执行有step决定
    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    @Bean
    public Step deciderDemoStep1() {
        return stepBuilderFactory.get("deciderDemoStep1")
                .tasklet((StepContribution, chunkContext) -> RepeatStatus.FINISHED)
                .build();
    }

    @Bean
    public Step deciderDemoStep2() {
        return stepBuilderFactory.get("deciderDemoStep2")
                .tasklet((StepContribution, chunkContext) -> RepeatStatus.FINISHED)
                .build();
    }

    @Bean
    public Step deciderDemoStep3() {
        return stepBuilderFactory.get("deciderDemoStep3")
                .tasklet((StepContribution, chunkContext) -> RepeatStatus.FINISHED)
                .build();
    }

    @Bean
    public JobExecutionDecider myDecider() {
        return new MyDecider();
    }

    @Bean
    public Job deciderDemoJob() {
        return jobBuilderFactory.get("deciderDemoJob")
                .start(deciderDemoStep1())
                .next(myDecider())
                .from(myDecider()).on("even").to(deciderDemoStep2())
                .from(myDecider()).on("odd").to(deciderDemoStep3())
                .from(deciderDemoStep3()).on("*").to(myDecider())
                .end()
                .build();
    }
}


