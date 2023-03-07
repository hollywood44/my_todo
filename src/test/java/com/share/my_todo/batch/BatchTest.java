package com.share.my_todo.batch;

import com.share.my_todo.service.todo.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Transactional
@SpringBootTest
public class BatchTest {

    @Autowired
    private TodoService todoService;

    @Test
    void 배치날짜테스트() {
        String tomorrowDate = todoService.dateToString(LocalDate.now().plusDays(1)).substring(1);
        System.out.println(tomorrowDate);
    }
}
