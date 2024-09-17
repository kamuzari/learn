package com.example.batch.config;

import com.example.batch.domain.Spot;
import com.example.batch.dto.PublicDataResponse;
import com.example.batch.dto.feign.PublicDataClient;
import com.example.batch.processor.PublicDataItemProcessor;
import com.example.batch.reader.PublicDataItemReader;
import com.example.batch.writer.PublicDataWriter;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.List;

@EnableScheduling
@EnableBatchProcessing
@Configuration
public class BatchConfig {
    @Value("${public-data.api.serviceKey}")
    private String serviceKey;

    private static final String PublicDataSql = "INSERT INTO spots (title, content_id, address, latitude, longitude, image1, image2, category) " +
            "VALUES (:title, :contentId, :address, :latitude, :longitude, :image1, :image2, :category)";

    @Bean
    public Job importPublicDataApiJson(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
        return new JobBuilder("importPublicDataApiJson", jobRepository)
                .listener(listener)
                .start(step1)
                .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @StepScope
    public PublicDataItemReader readPublicDataItem(PublicDataClient client, @Value("#{jobParameters['pageNo']}") int pageNo) {
        return new PublicDataItemReader(client, serviceKey, pageNo);
    }

    @Bean
    public PublicDataItemProcessor proccessPublicDataProcessor() {
        return new PublicDataItemProcessor();
    }


    @Bean
    public JdbcBatchItemWriter<Spot> jdbcBatchItemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Spot>()
                .dataSource(dataSource)
                .sql(PublicDataSql)
                .beanMapped()
                .build();
    }

    @Bean
    public PublicDataWriter<Spot> publicDataWriter(JdbcBatchItemWriter<Spot> jdbcBatchItemWriter) {
        return new PublicDataWriter<>(jdbcBatchItemWriter);
    }

    @Bean
    public Step getPublicDataStep(
            JobRepository jobRepository,
            PlatformTransactionManager platformTransactionManager,
            PublicDataItemReader reader,
            PublicDataItemProcessor processor,
            ItemWriter publicDataWriter
    ) {
        return new StepBuilder("publicDataStep", jobRepository)
                .<List<PublicDataResponse.Item>, List<Spot>>chunk(200, platformTransactionManager)
                .reader(reader)
                .processor(processor)
                .writer(publicDataWriter)
                .build();
    }
}
