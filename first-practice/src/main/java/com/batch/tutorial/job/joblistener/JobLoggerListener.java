package com.batch.tutorial.job.joblistener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

@Slf4j
public class JobLoggerListener implements JobExecutionListener {

    private static final String BEFORE_MESSAGE = "{} is running start";
    private static final String AFTER_MESSAGE = "{} is done. (status {})";

    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info(BEFORE_MESSAGE, jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        String jobName = jobExecution.getJobInstance().getJobName();
        BatchStatus batchStatus = jobExecution.getStatus();
        log.info(AFTER_MESSAGE, jobName, batchStatus);

        if (batchStatus == BatchStatus.FAILED) {
            log.error("{} job is failed.", jobName);
        }
    }
}
