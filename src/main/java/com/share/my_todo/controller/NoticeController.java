package com.share.my_todo.controller;

import com.share.my_todo.DTO.notice.NoticeDto;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/list")
    public String noticeListPage(Model model, @AuthenticationPrincipal Member member) {
        List<NoticeDto> list = noticeService.getNotReadNoticeList(member);
        model.addAttribute("noticeList", list);

        return "noticeList";
    }

    @GetMapping("/readNotice")
    public void readNotice(@AuthenticationPrincipal Member member) {
        noticeService.readNotice(member);
    }
}
