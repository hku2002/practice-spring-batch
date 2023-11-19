package com.batch.tutorial.job.validateparam;

import com.batch.tutorial.job.validateparam.validator.FileExtensionValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j
@Configuration
public class ValidateParamConfig {

    @Bean
    public Job validateParamJob(JobRepository jobRepository, Step validateParamStep) {
        return new JobBuilder("validateParam", jobRepository)
                .validator(new FileExtensionValidator())
                .start(validateParamStep)
                .build();
    }

    @Bean
    public Step validateParamStep(JobRepository jobRepository, Tasklet validateParamTasklet, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("validateParamStep", jobRepository)
                .tasklet(validateParamTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    @StepScope
    public Tasklet validateParamTasklet(@Value("#{jobParameters['fileName']}") String fileName) {
        return (contribution, chunkContext) -> {
            log.info(">>>>>> validate param!");
            return RepeatStatus.FINISHED;
        };
    }

}
