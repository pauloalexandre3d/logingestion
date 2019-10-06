package com.creditsuisse.logingestion.service;

import com.creditsuisse.logingestion.domain.EventLog;
import com.creditsuisse.logingestion.repository.EventsLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

public class EventWriter implements ItemWriter<EventLog>, StepExecutionListener {

    private final Logger logger = LoggerFactory.getLogger(EventWriter.class);

    @Autowired
    private EventsLog eventsLog;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        logger.debug("Event Writer initialized.");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        logger.debug("Event Writer ended.");
        return ExitStatus.COMPLETED;
    }

    @Override
    public void write(List<? extends EventLog> events) {

        for (EventLog event : events) {
            Optional<EventLog> eventLogSaved = eventsLog.findById(event.getId());
            if (eventLogSaved.isPresent()) {
                long duration = eventLogSaved.get().getTimestamp().until(event.getTimestamp(), ChronoUnit.MILLIS);
                duration = (duration < 0 ? -duration : duration);
                eventLogSaved.get().setLongDuration(Boolean.TRUE);
            } else {
                eventsLog.save(event);
            }

        }
    }
}
