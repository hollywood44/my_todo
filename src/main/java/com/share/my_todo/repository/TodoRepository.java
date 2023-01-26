package com.share.my_todo.repository;

import com.share.my_todo.entity.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Long, Todo> {
}
