package com.share.my_todo.service;

import com.share.my_todo.DTO.todo.TodoDto;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.entity.todo.Todo;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public interface TodoService {

    default TodoDto entityToDto(Todo entity) {
        TodoDto dto = TodoDto.builder()
                .todoId(entity.getTodoId())
                .todo(entity.getTodo())
                .finishDate(dateToString(entity.getFinishDate()))
                .progress(entity.getProgress())
                .memberId(entity.getMember().getMemberId())
                .build();
        return dto;
    }

    default Todo dtoToEntity(TodoDto dto) {
        Todo entity = Todo.builder()
                .todo(dto.getTodo())
                .finishDate(stringToDate(dto.getFinishDate()))
                .progress(dto.getProgress())
                .member(Member.builder().memberId(dto.getMemberId()).build())
                .build();
        return entity;
    }

    default LocalDate stringToDate(String date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate changeDate = LocalDate.parse(date, format);
        return changeDate;
    }

    default String dateToString(LocalDate date) {
        String changeDate = date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return changeDate;
    }

    /**
     * 할일 등록
     * @param dto [할일(String), 목표날짜(yyyyMMdd), 진행도(Progress), 회원아이디(String)]
     * @return 할일 아이디
     */
    Long postingTodo(TodoDto dto);

    /**
     * 할일 수정
     * @param dto [할일아이디(Long), 할일(String), 목표날짜(yyyyMMdd), 진행도(Progress), 회원아이디(String)]
     * @return 할일 아이디
     */
    Long modifyTodo(TodoDto dto);

    /**
     * 할일 완료
     * @param todoId
     * @return 완료된 할일아이디 리턴
     */
    Long completeMyTodo(Long todoId);

    /**
     * 할일 상세보기
     * @param todoId
     * @return
     */
    TodoDto getTodoDetail(Long todoId);

    /**
     * 할일 목록 가져오기
     * @param memberId
     * @return
     */
    List<TodoDto> getTodoList(String memberId);

    /**
     * 달성률 계산하기
     * @param todoList
     * @return
     */
    int getAchievementRate(String memberId);
}
