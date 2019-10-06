package com.creditsuisse.logingestion;

import com.creditsuisse.logingestion.config.SpringBatchConfig;
import com.creditsuisse.logingestion.domain.EventLog;
import com.creditsuisse.logingestion.service.EventReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.test.MetaDataInstanceFactory;
import org.springframework.batch.test.StepScopeTestExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringBatchConfig.class)
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class,
        StepScopeTestExecutionListener.class })
public class ReadEventTest {

    @Autowired
    private EventReader<EventLog> itemReader;

    public StepExecution getStepExecution() throws IOException {
        Fixture.getInstance().copyFile();

        StepExecution execution = MetaDataInstanceFactory.createStepExecution();
        execution.getExecutionContext().putString("logFilePath", System.getProperty("java.io.tmpdir").concat("/logfile.txt"));
        return execution;
    }

    @Test
    public void testReader() throws Exception {
        itemReader.beforeStep(getStepExecution());
        EventLog eventLog = itemReader.read();
        assertThat(eventLog, not(nullValue()));
        assertThat(eventLog.getId(), equalTo("scsmbstgra"));
    }

}
