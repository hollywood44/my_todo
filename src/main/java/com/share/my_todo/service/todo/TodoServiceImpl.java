package com.share.my_todo.service.todo;

import com.share.my_todo.DTO.todo.TodoDto;
import com.share.my_todo.util.SecurityUtil;
import com.share.my_todo.entity.common.TodoProgress;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.entity.todo.Todo;
import com.share.my_todo.exception.ErrorCode;
import com.share.my_todo.exception.exceptionClass.CommonException;
import com.share.my_todo.repository.todo.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;

    @Override
    public void postingTodo(TodoDto dto){
        if (dto.getFinishDate() == null || dto.getFinishDate().isEmpty())
            throw new CommonException(ErrorCode.POSTING_VALUE_EMPTY);
        if(dto.getTodo() == null || dto.getTodo().isEmpty())
            throw new CommonException(ErrorCode.POSTING_VALUE_EMPTY);

        dto.setFinishDate(dto.getFinishDate().replace("-",""));
        dto.setProgress(TodoProgress.Proceeding);
        dto.setMemberId(SecurityUtil.getCurrentMemberId());

        Todo todo = dtoToEntity(dto);

        todoRepository.save(todo).getTodoId();
    }

    @Override
    public void modifyTodo(TodoDto dto) {
        if (dto.getFinishDate() == null || dto.getFinishDate().isEmpty())
            throw new CommonException(ErrorCode.MODIFY_VALUE_EMPTY);
        if(dto.getTodo() == null || dto.getTodo().isEmpty())
            throw new CommonException(ErrorCode.MODIFY_VALUE_EMPTY);

        dto.setFinishDate(dto.getFinishDate().replace("-",""));
        dto.setProgress(TodoProgress.Proceeding);
        dto.setMemberId(SecurityUtil.getCurrentMemberId());

        Todo todo = todoRepository.findById(dto.getTodoId()).orElseThrow(() -> new CommonException(ErrorCode.TODO_NOT_FOUND));
        todo.modifyTodo(dto);

        todoRepository.save(todo);
    }

    @Override
    public Long completeMyTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId).get();
        todo.progressChangeToComplete();
        return todoRepository.save(todo).getTodoId();
    }

    @Override
    @Transactional
    public Long deleteTodo(Long todoId) {
        Todo check = todoRepository.findById(todoId).orElseThrow(() -> new CommonException(ErrorCode.TODO_NOT_FOUND));

        if (!check.getMember().getMemberId().equals(SecurityUtil.getCurrentMemberId())) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        } else {
            todoRepository.delete(check);
        }
        return todoId;
    }

    @Override
    public TodoDto getTodoDetail(Long todoId) {
        Todo todo = todoRepository.findById(todoId).get();
        if (!SecurityUtil.getCurrentMemberId().equals(todo.getMember().getMemberId())) {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }
        TodoDto detail = entityToDto(todo);

        return detail;
    }

    @Override
    public List<TodoDto> getNotDoneTodoList() {
        List<Todo> entityList = todoRepository.findByMemberAndProgress(Member.easyMakeMember(SecurityUtil.getCurrentMemberId()),TodoProgress.Proceeding);
        List<TodoDto> todoList = entityList.stream().map(entity -> entityToDto(entity)).collect(Collectors.toList());

        return todoList;
    }

    @Override
    public int getAchievementRate(List<Todo> todoList) {
        int totalCount = todoList.size();
        int completeThing = Long.valueOf(Optional.ofNullable(todoList.stream().filter(todo -> todo.getProgress().equals(TodoProgress.Success)).count()).orElse(0L)).intValue();

        return (int)((double) completeThing / (double) totalCount * 100.0);
    }

    @Override
    public List<Todo> getWeekTodoList(String memberId,int week) {
        List<Todo> weekTodoList = new ArrayList<>();

        if (week == 0) {
            Map<String, LocalDate> thisWeek = getWeekDate(0);

            weekTodoList = todoRepository
                    .lastWeekTodoListByMemberId(Member.builder().memberId(memberId).build()
                            , thisWeek.get("monday"), thisWeek.get("sunday"));
        } else {
            Map<String, LocalDate> lastWeek = getWeekDate(1);

            weekTodoList = todoRepository
                    .lastWeekTodoListByMemberId(Member.builder().memberId(memberId).build()
                            , lastWeek.get("monday"), lastWeek.get("sunday"));
        }

        return weekTodoList;
    }
}
