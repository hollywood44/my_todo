package com.share.my_todo.batch;

import com.share.my_todo.entity.member.Friend;
import com.share.my_todo.repository.friend.FriendRepository;
import com.share.my_todo.service.todo.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.*;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
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
public class FollowerDeleteBatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final FriendRepository friendRepository;
    private final TodoService todoService;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job followerDeleteJob() {
        return jobBuilderFactory.get("FollowerDeleteJob")
                .start(followerDeleteStep())
                .build();
    }

    @Bean
    @JobScope
    public Step followerDeleteStep() {
        return stepBuilderFactory.get("FollowerDeleteStep1")
                .<Friend, Friend>chunk(1000)
                .reader(friendReader(null))
                .writer(FriendItemWriter(null))
                .build();
    }

    @Bean
    @StepScope
    public JpaPagingItemReader<Friend> friendReader(@Value("#{jobParameters[requestDate]}") String requestDate) {
        String oneDaysAgo = todoService.dateToString(LocalDate.now().minusDays(1));

        return new JpaPagingItemReaderBuilder<Friend>()
                .name("jpaPagingItemReaderForFD")
                .entityManagerFactory(entityManagerFactory) //DataSource가 아닌 EntityManagerFactory를 통한 접근
                .pageSize(1000)
                .queryString("SELECT f FROM Friend f WHERE follow_status = 'Reject' and mod_date < " + oneDaysAgo + " ORDER BY friend_id ASC")  //ORDER 조건은 필수!
                .build();
    }


    @Bean
    @StepScope
    public JpaItemWriter<Friend> FriendItemWriter(@Value("#{jobParameters[requestDate]}") String requestDate) {
        JpaItemWriterCustom<Friend> writer = new JpaItemWriterCustom<>();
        writer.setEntityManagerFactory(entityManagerFactory);
        writer.setUsePersist(false);

        return writer;
    }

}
