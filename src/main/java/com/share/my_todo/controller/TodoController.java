package com.share.my_todo.controller;

import com.share.my_todo.DTO.todo.TodoDto;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.service.todo.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/todo")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/main")
    public String mainPage(@AuthenticationPrincipal Member member, Model model) {
        List<TodoDto> notYetList = todoService.getTodoList(member.getMemberId());
        model.addAttribute("todoList", notYetList);

        return "main";
    }

    @PostMapping("/posting")
    public String posting(TodoDto posting, @AuthenticationPrincipal Member member) {
        todoService.postingTodo(posting, member);

        return "redirect:/todo/main";
    }

    @GetMapping("/complete-todo")
    public String completeTodo(@RequestParam("todoId") Long todoId) {
        todoService.completeMyTodo(todoId);

        return "redirect:/todo/main";
    }

    @GetMapping("/modify-todo")
    public String modifyTodoPage(@RequestParam("todoId") Long todoId, Model model, @AuthenticationPrincipal Member member, RedirectAttributes redirectAttributes) {
        TodoDto item = todoService.getTodoDetail(todoId);

        if (!member.getMemberId().equals(item.getMemberId())) {
            redirectAttributes.addFlashAttribute("error", "접근 권한이 없습니다!");
            return "redirect:/todo/main";
        } else {
            model.addAttribute("item", item);
            return "todoModify";
        }

    }

    @PostMapping("/modify")
    public String modify(TodoDto modify, @AuthenticationPrincipal Member member) {
        todoService.modifyTodo(modify, member);

        return "redirect:/todo/main";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam(value = "todoId") Long todoId) {
        todoService.deleteTodo(todoId);

        return "redirect:/todo/main";
    }
}
