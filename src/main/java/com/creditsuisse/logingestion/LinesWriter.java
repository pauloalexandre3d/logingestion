package com.creditsuisse.logingestion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class LinesWriter implements ItemWriter<EventLog>, StepExecutionListener {
    private final Logger logger = LoggerFactory.getLogger(LinesWriter.class);

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.debug("Line Writer initialized.");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("Line Writer ended.");
        return ExitStatus.COMPLETED;
    }

    @Override
    public void write(List<? extends EventLog> events) {
        System.out.println(events);
        for (EventLog event : events) {
            logger.debug("Wrote line " + event.toString());
        }
    }
}
