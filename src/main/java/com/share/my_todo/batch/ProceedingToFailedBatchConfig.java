package com.share.my_todo.batch;

import com.share.my_todo.entity.todo.Todo;
import com.share.my_todo.service.todo.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
@RequiredArgsConstructor
@Log4j2
@EnableBatchProcessing
public class ProceedingToFailedBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final TodoService todoService;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job PtoFJob() {
        return jobBuilderFactory.get("ProceedingToFailedJob")
                .start(ProceedingToFailedStep())
                .build();
    }

    @Bean
    @JobScope
    public Step ProceedingToFailedStep() {
        return stepBuilderFactory.get("ProceedingToFailedStep1")
                .<Todo, Todo>chunk(1000)
                .reader(PtoFReader(null))
                .processor(PtoFProcessor(null))
                .writer(PtoFWriter(null))
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Todo> PtoFReader(@Value("#{jobParameters[requestDate]}") String requestDate) {
        LocalDate yesterdayOrigin = LocalDate.now().minusDays(1);
        String yesterday = yesterdayOrigin.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        return new JpaPagingItemReaderBuilder<Todo>()
                .name("jpaPagingItemReaderForPtoFJob")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(1000)
                .queryString("SELECT t FROM Todo t WHERE finish_date = " + yesterday + " AND NOT Progress = 'Failed' ORDER BY todo_id ASC")
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Todo, Todo> PtoFProcessor(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return new ItemProcessor<Todo, Todo>() {
            @Override
            public Todo process(Todo item) throws Exception {
                item.progressChangeToFailed();
                return item;
            }
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<Todo> PtoFWriter(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return new JpaItemWriterBuilder<Todo>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
