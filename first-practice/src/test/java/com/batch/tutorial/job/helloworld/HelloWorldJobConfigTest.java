package com.batch.tutorial.job.helloworld;

import com.batch.tutorial.job.SpringBatchTestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBatchTest
@SpringBootTest(classes = {HelloWorldJobConfig.class, SpringBatchTestConfig.class})
class HelloWorldJobConfigTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Test
    @DisplayName("Hello world 출력 성공")
    void success() throws Exception {
        JobExecution jobExecution = jobLauncherTestUtils.launchJob();

        assertThat(jobExecution).isEqualTo(ExitStatus.COMPLETED);
    }

}