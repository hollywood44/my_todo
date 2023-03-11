package com.share.my_todo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class RestTestController {

    @GetMapping
    public String test() {
        return "it is from spring server";
    }
}
