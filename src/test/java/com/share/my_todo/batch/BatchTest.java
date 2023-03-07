package com.share.my_todo.batch;

import com.share.my_todo.service.todo.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Transactional
@SpringBootTest
public class BatchTest {

    @Autowired
    private TodoService todoService;

    @Test
    void 배치날짜테스트() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        String changeDate = yesterday.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        System.out.println(changeDate);
    }
}
