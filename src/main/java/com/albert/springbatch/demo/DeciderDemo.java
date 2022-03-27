package com.albert.springbatch.demo;

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

/**
 * Albert
 */
@Configuration
public class DeciderDemo {

    //注入创建任务对象的对象
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    //任务的执行由step决定
    //注入创建step对象的对象
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    //创建step
    @Bean
    public Step deciderDemoStep1() {
        return stepBuilderFactory.get("deciderDemoStep1")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("deciderDemoStep1");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step deciderDemoStep2() {
        return stepBuilderFactory.get("deciderDemoStep2")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("even");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step deciderDemoStep3() {
        return stepBuilderFactory.get("deciderDemoStep3")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("odd");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    //创建决策权
    @Bean
    public JobExecutionDecider myDecider() {
        return new MyDecider();
    }


    //创建任务
    //创建任务对象
    @Bean
    public Job deciderDemoJob() {
        return jobBuilderFactory.get("deciderDemoJob")
                .start(deciderDemoStep1())
                .next(myDecider())
                .from(myDecider()).on("even").to(deciderDemoStep2())
                .from(myDecider()).on("odd").to(deciderDemoStep3())
                //count++为1,首先会执行deciderDemoStep3,
                //无论step3执行完返回什么再次回到决策器，这时候count++变成2,接着执行step2,最后结束
                //.from(deciderDemoStep3()).on("*").to(myDecider())
                .end()
                .build();

    }
}
