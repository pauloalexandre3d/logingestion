package com.creditsuisse.logingestion;

import com.creditsuisse.logingestion.config.SpringBatchConfig;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(SpringBatchConfig.class);
        context.refresh();

        JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("job");

        System.out.println("Starting the batch job");
        try {
            System.out.println("Enter the file path: ");
            Scanner scanner = new Scanner(System.in);
            String filePath = scanner.nextLine();

            LocalTime start = LocalTime.now();

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("logFilePath", filePath)
                    .toJobParameters();

            JobExecution execution = jobLauncher.run(job, jobParameters);

            LocalTime end = LocalTime.now();
            System.out.println("------------- Time execution: " + start.until(end, ChronoUnit.MILLIS));

            System.out.println("Job Status : " + execution.getStatus());
            System.out.println("Job completed");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Job failed");
        }
    }
}
