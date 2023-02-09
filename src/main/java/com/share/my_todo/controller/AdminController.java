package com.share.my_todo.controller;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping
    public String test() {
        return "spring admin controller";
    }




}
