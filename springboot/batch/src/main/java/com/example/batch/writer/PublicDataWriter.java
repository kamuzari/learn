package com.example.batch.writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import java.util.List;

public class PublicDataWriter<T> implements ItemWriter<List<T>> {
    private static final Logger log = LoggerFactory.getLogger(PublicDataWriter.class);
    private final JdbcBatchItemWriter<T> jdbcBatchItemWriter;

    public PublicDataWriter(JdbcBatchItemWriter<T> jdbcBatchItemWriter) {
        this.jdbcBatchItemWriter = jdbcBatchItemWriter;
    }

    @Override
    public void write(Chunk<? extends List<T>> chunk) throws Exception {
        log.info("PublicDataWriter -> write call -> {}", chunk);
        for (List<T> ts : chunk) {
            ts.stream().forEach(v-> {
                try {
                    jdbcBatchItemWriter.write(new Chunk<>(v));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        log.info("============= [END] PublicDataWriter -> write call  =============== ");
    }
}
