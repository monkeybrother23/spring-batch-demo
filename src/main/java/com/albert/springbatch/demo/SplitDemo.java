package com.albert.springbatch.demo;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * Albert
 * 并发执行
 */
@Configuration
public class SplitDemo {

    //注入创建任务对象的对象
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    //任务的执行由step决定
    //注入创建step对象的对象
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    //创建任务对象
    @Bean
    public Job splitDemoJob() {
        return jobBuilderFactory.get("splitDemoJob")
                .start(flowSplitDemoJobstep1()).split(new SimpleAsyncTaskExecutor())
                .add(flowSplitDemoJobstep2())
                .end().build();

    }

    @Bean
    public Step splitDemoJobstep1() {
        return stepBuilderFactory.get("splitDemoJobstep1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("hello world splitDemoJobstep1");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step splitDemoJobstep2() {
        return stepBuilderFactory.get("splitDemoJobstep2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("hello world splitDemoJobstep2");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step splitDemoJobstep3() {
        return stepBuilderFactory.get("splitDemoJobstep3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("hello world splitDemoJobstep3");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    //创建Flow对象，指明Flow对象包含那些ste
    @Bean
    public Flow flowSplitDemoJobstep1() {
        return new FlowBuilder<Flow>("flowSplitDemoJobstep1")
                .start(splitDemoJobstep1())
                .next(splitDemoJobstep3())
                .build();
    }


    //创建Flow对象，指明Flow对象包含那些ste
    @Bean
    public Flow flowSplitDemoJobstep2() {
        return new FlowBuilder<Flow>("flowSplitDemoJobstep2")
                .start(splitDemoJobstep2())
                .build();
    }
}
