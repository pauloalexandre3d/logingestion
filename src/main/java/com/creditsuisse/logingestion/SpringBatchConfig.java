package com.creditsuisse.logingestion;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
public class SpringBatchConfig {

    @Autowired private JobBuilderFactory jobBuilderFactory;

    @Autowired private StepBuilderFactory steps;

    @Bean
    public JobLauncherTestUtils jobLauncherTestUtils() {
        return new JobLauncherTestUtils();
    }

    @Bean
    public JobRepository jobRepository() throws Exception {
        MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean();
        factory.setTransactionManager(transactionManager());
        return factory.getObject();
    }

	@Bean
	public PlatformTransactionManager transactionManager() {
		return new ResourcelessTransactionManager();
	}

    @Bean
    public JobLauncher jobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository());
        return jobLauncher;
    }

    @Bean
    public ItemReader<EventLog> itemReader() {
        return new EventReader<>();
    }

    @Bean
    public ItemWriter<EventLog> itemWriter() {
        return new LinesWriter();
    }

    @Bean
    protected Step processLines(ItemReader<EventLog> reader, ItemWriter<EventLog> writer) {
        return steps.get("processLines").<EventLog, EventLog> chunk(1)
                .reader(reader)
                .writer(writer)
                .readerIsTransactionalQueue()
                .build();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory
                .get("chunksJob")
                .preventRestart()
                .start(processLines(itemReader(), itemWriter()))
                .build();
    }

}
