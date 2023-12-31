package com.batch.tutorial.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class SampleScheduler {

    private final Job helloWorldJob;
    private final Job orderToSettlementJob;
    private final JobLauncher jobLauncher;

    @Scheduled(cron = "0 */1 * * * *")
    public void helloWorldJobRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParameters(
                Collections.singletonMap("requestTime", new JobParameter<>(LocalDateTime.now(), LocalDateTime.class))
        );

        jobLauncher.run(helloWorldJob, jobParameters);
    }

    @Scheduled(cron = "*/30 * * * * *")
    public void orderToSettlementJobRun() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        JobParameters jobParameters = new JobParameters(
                Collections.singletonMap("requestTime", new JobParameter<>(LocalDateTime.now(), LocalDateTime.class))
        );
        jobLauncher.run(orderToSettlementJob, jobParameters);
    }
}
