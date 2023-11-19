package com.batch.tutorial.job.helloworld;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class HelloWorldJobConfig {

    @Bean
    public Job helloWorldJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("helloWorldJob", jobRepository)
                .start(helloWorldStep(jobRepository, platformTransactionManager))
                .build();
    }

    @Bean
    public Step helloWorldStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("helloWorldStep", jobRepository)
                .tasklet(helloWorldTasklet(), platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet helloWorldTasklet() {
        return (contribution, chunkContext) -> {
            log.info(">>>>>> Hello World!");
            return RepeatStatus.FINISHED;
        };
    }

}
