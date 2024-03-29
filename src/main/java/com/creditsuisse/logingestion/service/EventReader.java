package com.creditsuisse.logingestion.service;

import com.creditsuisse.logingestion.domain.EventLog;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class EventReader<T> implements ItemReader<EventLog>, StepExecutionListener {

    private final Logger logger = LoggerFactory.getLogger(EventReader.class);

    private BufferedReader reader;

    @Override
    public void beforeStep(StepExecution stepExecution) {

        String logFilePath;
        if (stepExecution.getExecutionContext().containsKey("logFilePath")) {
            logFilePath = stepExecution.getExecutionContext().getString("logFilePath");
        } else {
            logFilePath = stepExecution.getJobExecution().getJobParameters().getString("logFilePath");
        }

        try {
            reader = Files.newBufferedReader(Paths.get(logFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.debug("Event Reader initialized.");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        try {
            reader.close();
        } catch (IOException e) {
            logger.error("Error while closing reader.");
        }
        logger.debug("Event Reader ended.");
        return ExitStatus.COMPLETED;
    }

    @Override
    public EventLog read() {
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        Gson gson = new GsonBuilder().registerTypeAdapter(
                LocalDateTime.class, new DateTimeDeserializer()).create();

        EventLog event = gson.fromJson(line, EventLog.class);

        if (line != null) logger.debug("Read line: " + event.toString());

        return event;
    }
}
