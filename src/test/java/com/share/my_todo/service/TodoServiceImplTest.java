package com.share.my_todo.service;

import com.share.my_todo.DTO.todo.TodoDto;
import com.share.my_todo.entity.common.TodoProgress;
import com.share.my_todo.entity.todo.Todo;
import com.share.my_todo.repository.todo.TodoRepository;
import com.share.my_todo.service.todo.TodoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

@SpringBootTest
@Transactional
class TodoServiceImplTest {

    @Autowired
    TodoService todoService;

    @Autowired
    TodoRepository todoRepository;


    @Test
    @Rollback(value = false)
    void todoPostingTest() {
        IntStream.rangeClosed(1,3).forEach(i ->{
            TodoDto dto = TodoDto.builder()
                    .todo("todo "+i)
                    .progress(TodoProgress.Proceeding)
                    .finishDate("20230202")
                    .memberId("member2")
                    .build();
//            todoService.postingTodo(dto);
                }
        );

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

//        System.out.println(todoService.modifyTodo(dto));
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
        List<Todo> thisWeeklist = todoService.getWeekTodoList("member2",1);
        System.out.println("저번주 달성률은 " + todoService.getAchievementRate(thisWeeklist) + "% 입니다!");

        List<Todo> lastweeklist = todoService.getWeekTodoList("member2",0);
        System.out.println("이번주 달성률은 " + todoService.getAchievementRate(lastweeklist) + "% 입니다!");
    }

    @Test
    void 목록보기() {

    }

    @Test
    void 날짜테스트() {
        Map<String, LocalDate> lastWeek = todoService.getWeekDate(1);
        System.out.println("지난주 월요일 : " + lastWeek.get("monday"));
        System.out.println("지난주 일요일 : " + lastWeek.get("sunday"));

        Map<String, LocalDate> ThisWeek = todoService.getWeekDate(0);
        System.out.println("이번주 월요일 : " + ThisWeek.get("monday"));
        System.out.println("이번주 일요일 : " + ThisWeek.get("sunday"));
    }

    @Test
    @Rollback(value = false)
    void 할일삭제() {
        System.out.println(todoService.deleteTodo(37L));
    }
}