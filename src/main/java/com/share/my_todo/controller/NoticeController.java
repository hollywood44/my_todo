package com.share.my_todo.controller;

import com.share.my_todo.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notice")
public class NoticeController {

    private NoticeService noticeService;
}
