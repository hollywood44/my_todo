package com.share.my_todo.controller;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    @GetMapping
    public String test() {
        return "spring admin controller";
    }




}
