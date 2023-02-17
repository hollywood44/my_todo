package com.share.my_todo.batch;

import com.share.my_todo.entity.notice.Notice;
import com.share.my_todo.entity.todo.Todo;
import com.share.my_todo.service.notice.NoticeService;
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

@Configuration
@RequiredArgsConstructor
@Log4j2
@EnableBatchProcessing
public class TomorrowBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final NoticeService noticeService;
    private final TodoService todoService;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job notificationJob() {
        return jobBuilderFactory.get("tomorrowNotificationJob")
                .start(sendWeekNotification())
                .build();
    }

    @Bean
    @JobScope
    public Step sendWeekNotification() {
        return stepBuilderFactory.get("tomorrowNotificationStep1")
                .<Todo, Notice>chunk(1000)
                .reader(todoReader(null))
                .processor(todoItemProcessor(null))
                .writer(noticeItemWriter(null))
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Todo> todoReader(@Value("#{jobParameters[requestDate]}") String requestDate) {
        String tomorrowDate = todoService.dateToString(LocalDate.now().plusDays(1));

        return new JpaPagingItemReaderBuilder<Todo>()
                .name("jpaPagingItemReader")
                .entityManagerFactory(entityManagerFactory) //DataSource가 아닌 EntityManagerFactory를 통한 접근
                .pageSize(1000)
                .queryString("SELECT t FROM Todo t WHERE finish_date = " + tomorrowDate + " ORDER BY todo_id ASC")  //ORDER 조건은 필수!
                .build();
    }

    @Bean
    @StepScope
    public ItemProcessor<Todo, Notice> todoItemProcessor(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return new ItemProcessor<Todo, Notice>() {
            @Override
            public Notice process(Todo item) throws Exception {
                Notice notice = noticeService.makeFinishDateTomorrowNotice(item.getMember().getMemberId(), item.getTodo());
                return notice;
            }
        };
    }

    @Bean
    @StepScope
    public JpaItemWriter<Notice> noticeItemWriter(@Value("#{jobParameters[requestDate]}") String requestDate) {
        return new JpaItemWriterBuilder<Notice>()
                .entityManagerFactory(entityManagerFactory)
                .build();
    }
}
