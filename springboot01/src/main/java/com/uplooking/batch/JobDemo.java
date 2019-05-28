package com.uplooking.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class JobDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    //任务的执行有step决定
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job jobDemoJob() {
        return jobBuilderFactory.get("jobDemo")
//                .start(step1())
//                .next(step2())
//                .next(step3())
                .start(step1())//step1完成，才执行step2 on from to
                .on("COMPLETED").to(step2())
                .from(step2()).on("COMPLETED")
                .stopAndRestart(step3())//.fail()//.to(step3())
                .from(step3()).end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet((StepContribution, chunkContext) -> RepeatStatus.FINISHED)
                .build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory.get("step2")
                .tasklet((StepContribution, chunkContext) -> RepeatStatus.FINISHED)
                .build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .tasklet((StepContribution, chunkContext) -> RepeatStatus.FINISHED)
                .build();
    }
}
