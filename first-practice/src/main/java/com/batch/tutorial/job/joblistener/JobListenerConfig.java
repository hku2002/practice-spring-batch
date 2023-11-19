package com.batch.tutorial.job.joblistener;

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

/**
 * 실행 전,후 로그 출력 하는 예제
 * params: --spring.batch.job.name=jobListener
 */
@Slf4j
@Configuration
public class JobListenerConfig {

    @Bean
    public Job jobListenerJob(JobRepository jobRepository, Step jobListenerStep) {
        return new JobBuilder("jobListener", jobRepository)
                .start(jobListenerStep)
                .build();
    }

    @Bean
    public Step jobListenerStep(JobRepository jobRepository, Tasklet jobListenerTasklet, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("jobListenerStep", jobRepository)
                .tasklet(jobListenerTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet jobListenerTasklet() {
        return (contribution, chunkContext) -> {
            log.info(">>>>>> jobListenerTasklet");
            return RepeatStatus.FINISHED;
        };
    }

}
