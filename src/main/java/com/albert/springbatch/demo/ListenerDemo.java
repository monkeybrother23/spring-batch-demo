package com.albert.springbatch.demo;

import com.albert.springbatch.listener.MyChunkListener;
import com.albert.springbatch.listener.MyJobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

/**
 * Albert
 */
@Configuration
public class ListenerDemo {

    //注入创建任务对象的对象
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    //任务的执行由step决定
    //注入创建step对象的对象
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    //创建任务对象
    @Bean
    public Job listenerDemoJob() {
        return jobBuilderFactory.get("listenerDemoJob")
                .start(listenerJobDemoStep1()).listener(new MyJobListener())
                .build();
    }

    @Bean
    public Step listenerJobDemoStep1() {
        return stepBuilderFactory.get("listenerJobDemoStep1")
                .<String, String>chunk(2)//读二个处理一次
                .faultTolerant()
                .listener(new MyChunkListener())
                .reader(read())
                .writer(write())
                .build();
    }

    @Bean
    public ItemWriter<String> write() {
        return list -> {
            for (String temp : list) {
                System.out.println(temp);
            }
        };
    }

    @Bean
    public ItemReader<String> read() {
        return new ListItemReader<>(Arrays.asList("java", "mysql", "Oracle", "mybatis", "spring"));
    }
    //start:listenerDemoJob
//    before:listenerJobDemoStep1
//            java
//    mysql
//    after:listenerJobDemoStep1
//    before:listenerJobDemoStep1
//            Oracle
//    mybatis
//    after:listenerJobDemoStep1
//    before:listenerJobDemoStep1
//            spring
//    after:listenerJobDemoStep1
//    end:listenerDemoJob
}
