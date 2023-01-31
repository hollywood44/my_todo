package com.share.my_todo.service;

import com.share.my_todo.DTO.todo.TodoDto;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.entity.todo.Todo;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    default Map<String,LocalDate> getLastWeekDate() {
        Map<String, LocalDate> lastWeek = new HashMap<>();
        Calendar cal = Calendar.getInstance();

        cal.add(Calendar.DATE,-7);
        int nWeek = cal.get(Calendar.DAY_OF_WEEK);

        cal.add(Calendar.DATE,2-nWeek);
        int nMonth = cal.get(Calendar.MONDAY) + 1;
        String dayLastWeekFrom = cal.get(Calendar.YEAR) + (nMonth<10?"0"+nMonth:nMonth+"") + (cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE)+"");
        lastWeek.put("monday", stringToDate(dayLastWeekFrom));

        cal.add(Calendar.DATE, 6);//월요일 부터 일요일까지의 날짜 더함
        nMonth  = cal.get(Calendar.MONTH)+1;
        String dayLastWeekTo = cal.get(Calendar.YEAR) + (nMonth<10?"0"+nMonth:nMonth+"") + (cal.get(Calendar.DATE)<10?"0"+cal.get(Calendar.DATE):cal.get(Calendar.DATE)+"");
        lastWeek.put("sunday", stringToDate(dayLastWeekTo));

        return lastWeek;
    }

    /**
     * 지난주 할일 불러옴
     * @return
     */
    List<Todo> getLastWeekTodoList(String memberId);

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
     * @param todoList 목표일이 지난주(월~일) 또는 특정일인 할일목록을 불러옴
     * @return 달성률
     */
    int getAchievementRate(List<Todo> todoList);
}
