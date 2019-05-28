package com.uplooking.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class FlowDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    //任务的执行有step决定
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step flowDemoStep1() {
        return stepBuilderFactory.get("flowDemoStep1")
                .tasklet((StepContribution, chunkContext) -> RepeatStatus.FINISHED)
                .build();
    }

    @Bean
    public Step flowDemoStep2() {
        return stepBuilderFactory.get("flowDemoStep2")
                .tasklet((StepContribution, chunkContext) -> RepeatStatus.FINISHED)
                .build();
    }

    @Bean
    public Step flowDemoStep3() {
        return stepBuilderFactory.get("flowDemoStep3")
                .tasklet((StepContribution, chunkContext) -> RepeatStatus.FINISHED)
                .build();
    }

    @Bean
    public Flow flowDemoFlow() {
        return new FlowBuilder<Flow>("flowDemoFlow")
                .start(flowDemoStep1())
                .next(flowDemoStep2())
                .build();
    }

    @Bean
    public Job flowDemoJob() {
        return jobBuilderFactory.get("flowDemoJob")
                .start(flowDemoFlow())
                .next(flowDemoStep3())
                .end().build();
    }

}
