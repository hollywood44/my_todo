package com.share.my_todo.controller;

import com.share.my_todo.DTO.todo.TodoDto;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.service.todo.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/list")
    public ResponseEntity<List<TodoDto>> getNotDoneTodoList() {
        List<TodoDto> notDoneList = todoService.getNotDoneTodoList();

        return ResponseEntity.status(HttpStatus.OK).body(notDoneList);
    }

    @GetMapping("/detail/{todoId}")
    public ResponseEntity<TodoDto> getTodoDetail(@PathVariable(name = "todoId") Long todoId) {
        TodoDto detailData = todoService.getTodoDetail(todoId);

        return ResponseEntity.status(HttpStatus.OK).body(detailData);
    }

    @PostMapping("/posting")
    public ResponseEntity<?> todoPosting(@RequestBody TodoDto posting) {
        todoService.postingTodo(posting);

        return ResponseEntity.status(HttpStatus.OK).body("todo posting complete");
    }

    @PostMapping("/complete")
    public ResponseEntity<?> completeTodo(@RequestBody Map<String,Long> todoMap) {
        todoService.completeMyTodo(todoMap.get("todoId"));

        return ResponseEntity.status(HttpStatus.OK).body("todo complete");
    }

    @PostMapping("/modify")
    public ResponseEntity<?> modifyTodo(@RequestBody TodoDto modifyData) {
        todoService.modifyTodo(modifyData);

        return ResponseEntity.status(HttpStatus.OK).body("todo modify complete");
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody Map<String, Long> todoMap) {
        todoService.deleteTodo(todoMap.get("todoId"));

        return ResponseEntity.status(HttpStatus.OK).body("todo delete complete");
    }
}
