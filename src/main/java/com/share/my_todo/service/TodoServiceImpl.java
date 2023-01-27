package com.share.my_todo.service;

import com.share.my_todo.DTO.todo.TodoDto;
import com.share.my_todo.entity.todo.Todo;
import com.share.my_todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
