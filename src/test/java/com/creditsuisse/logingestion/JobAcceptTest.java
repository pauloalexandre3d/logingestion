package com.creditsuisse.logingestion;

import com.creditsuisse.logingestion.config.SpringBatchConfig;
import com.creditsuisse.logingestion.repository.EventsLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBatchTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringBatchConfig.class)
public class JobAcceptTest {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    @Autowired
    private EventsLog eventsLog;

    @Test
    public void testShouldAssertJobCompletedWithSuccess() throws Exception {
        Fixture.getInstance().copyFile();

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("logFilePath", System.getProperty("java.io.tmpdir").concat("/logfile.txt"))
                .toJobParameters();

        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        assertThat(jobExecution.getExitStatus().getExitCode(), equalTo("COMPLETED"));
        assertThat(eventsLog.findAll().size(), equalTo(3));
    }


}
