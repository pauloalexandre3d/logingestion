package com.creditsuisse.logingestion;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.CommandLineJobRunner;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Scanner;

public class App extends CommandLineJobRunner {

    public static void main(final String[] args) {

//        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
//        context.register(SpringBatchConfig.class);
//        context.refresh();
//
//        final JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
//        final Job job = (Job) context.getBean("job");
//        System.out.println("Starting the batch job");
//        try {
//            System.out.println("Enter the file path: ");
//            Scanner scanner = new Scanner(System.in);
//            String filePath = scanner.nextLine();
//
//            JobParameters jobParameters = new JobParametersBuilder()
//                    .addString("logFilePath", filePath)
//                    .toJobParameters();
//
//            final JobExecution execution = jobLauncher.run(job, jobParameters);
//
//            System.out.println("Job Status : " + execution.getStatus());
//
//        } catch (final Exception e) {
//            e.printStackTrace();
//            System.out.println("Job failed");
//        }

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

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("logFilePath", filePath)
                    .toJobParameters();

            JobExecution execution = jobLauncher.run(job, jobParameters);
            System.out.println("Job Status : " + execution.getStatus());
            System.out.println("Job completed");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Job failed");
        }
    }
}
