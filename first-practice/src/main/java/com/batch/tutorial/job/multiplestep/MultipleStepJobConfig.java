package com.batch.tutorial.job.multiplestep;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * step 순서에 따른 구동 예제
 * params: --spring.batch.job.name=multipleStepJob
 */
@Slf4j
@Configuration
public class MultipleStepJobConfig {

    @Bean
    public Job multipleStepJob(JobRepository jobRepository, Step multipleStep1, Step multipleStep2, Step multipleStep3) {
        return new JobBuilder("multipleStepJob", jobRepository)
                .start(multipleStep1)
                .next(multipleStep2)
                .next(multipleStep3)
                .build();
    }

    @Bean
    @JobScope
    public Step multipleStep1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("multipleStep1", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>> step1");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    @JobScope
    public Step multipleStep2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("multipleStep2", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>> step2");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

    @Bean
    @JobScope
    public Step multipleStep3(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("multipleStep3", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info(">>>>>> step3");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build();
    }

}
