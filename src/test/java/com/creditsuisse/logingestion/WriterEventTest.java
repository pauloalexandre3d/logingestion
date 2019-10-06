package com.creditsuisse.logingestion;

import com.creditsuisse.logingestion.config.SpringBatchConfig;
import com.creditsuisse.logingestion.domain.EventLog;
import com.creditsuisse.logingestion.repository.EventsLog;
import com.creditsuisse.logingestion.service.EventWriter;
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
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringBatchConfig.class)
@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class,
        StepScopeTestExecutionListener.class })
public class WriterEventTest {

    @Autowired
    private EventWriter itemWriter;

    @Autowired
    private EventsLog eventsLog;

    public StepExecution getStepExecution() throws IOException {
        StepExecution execution = MetaDataInstanceFactory.createStepExecution();
        return execution;
    }

    @Test
    public void testWriter() throws Exception {
        itemWriter.beforeStep(getStepExecution());

        List<EventLog> listEvents = new ArrayList<>();
        EventLog event1 = new EventLog();
        event1.setId("Event1");
        listEvents.add(event1);
        EventLog event2 = new EventLog();
        event2.setId("Event2");
        listEvents.add(event2);

        itemWriter.write(listEvents);
        assertThat(eventsLog.findAll().size(), equalTo(2));
    }

}
