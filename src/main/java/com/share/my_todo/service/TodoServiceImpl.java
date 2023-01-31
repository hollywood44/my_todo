package com.share.my_todo.service;

import com.share.my_todo.DTO.todo.TodoDto;
import com.share.my_todo.entity.common.TodoProgress;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.entity.todo.Todo;
import com.share.my_todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;

    @Override
    public Long postingTodo(TodoDto dto) {
        Todo todo = dtoToEntity(dto);
        return todoRepository.save(todo).getTodoId();
    }

    @Override
    public Long modifyTodo(TodoDto dto) {
        Todo todo = todoRepository.findById(dto.getTodoId()).get();
        todo.modifyTodo(dto);

        return todoRepository.save(todo).getTodoId();
    }

    @Override
    public Long completeMyTodo(Long todoId) {
        Todo todo = todoRepository.findById(todoId).get();
        todo.progressChangeToComplete();
        return todoRepository.save(todo).getTodoId();
    }

    @Override
    public TodoDto getTodoDetail(Long todoId) {
        Todo todo = todoRepository.findById(todoId).get();
        TodoDto detail = entityToDto(todo);

        return detail;
    }

    @Override
    public List<TodoDto> getTodoList(String memberId) {
        List<Todo> entityList = todoRepository.findAllByMember(Member.builder().memberId(memberId).build());
        List<TodoDto> todoList = entityList.stream().map(entity -> entityToDto(entity)).collect(Collectors.toList());

        return todoList;
    }

    @Override
    public int getAchievementRate(String memberId) {
        List<Todo> entityList = todoRepository.findAllByMember(Member.builder().memberId(memberId).build());

        int totalCount = entityList.size();
        int completeThing = Long.valueOf(Optional.ofNullable(entityList.stream().filter(todo -> todo.getProgress().equals(TodoProgress.Success)).count()).orElse(0L)).intValue();

        return (int)((double) completeThing / (double) totalCount * 100.0);
    }
}
