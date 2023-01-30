package com.share.my_todo.service;

import com.share.my_todo.DTO.todo.TodoDto;
import com.share.my_todo.entity.common.TodoProgress;
import com.share.my_todo.entity.common.TodoProgressNoticeMessage;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TodoServiceImplTest {

    @Autowired
    TodoService todoService;


    @Test
    @Rollback(value = false)
    void todoPostingTest() {
        TodoDto dto = TodoDto.builder()
                .todo("todo 1")
                .progress(TodoProgress.Proceeding)
                .finishDate("20230129")
                .memberId("member1")
                .build();

        System.out.println(todoService.postingTodo(dto));
    }

    @Test
    @Rollback(value = false)
    void todoModifyTest() {
        TodoDto dto = TodoDto.builder()
                .todoId(1L)
                .todo("todo 1 - modify")
                .progress(TodoProgress.Proceeding)
                .finishDate("20230130")
                .memberId("member1")
                .build();

        System.out.println(todoService.modifyTodo(dto));
    }

    @Test
    @Rollback(value = false)
    void 할일완료() {
        todoService.completeMyTodo(1L);
    }

    @Test
    void 할일디테일보기() {
        System.out.println(todoService.getTodoDetail(1L));
    }
}