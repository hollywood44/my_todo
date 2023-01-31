package com.share.my_todo.service;

import com.share.my_todo.DTO.todo.TodoDto;
import com.share.my_todo.entity.common.TodoProgress;
import com.share.my_todo.entity.common.TodoProgressNoticeMessage;
import com.share.my_todo.entity.todo.Todo;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
                .todo("todo 6")
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
        todoService.completeMyTodo(5L);
    }

    @Test
    void 할일디테일보기() {
        System.out.println(todoService.getTodoDetail(1L));
    }

    @Test
    void 달성률보기() {
        List<Todo> lastweeklist = todoService.getLastWeekTodoList("member1");

        System.out.println("달성률은 " + todoService.getAchievementRate(lastweeklist) + "% 입니다!");
    }

    @Test
    void 목록보기() {
        System.out.println(todoService.getTodoList("member1"));
    }

    @Test
    void 날짜테스트() {
        Map<String, LocalDate> lastWeek = todoService.getLastWeekDate();
        System.out.println("월요일 : " + lastWeek.get("monday"));
        System.out.println("일요일 : " + lastWeek.get("sunday"));
    }
}