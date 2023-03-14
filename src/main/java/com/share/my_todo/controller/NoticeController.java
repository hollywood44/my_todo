package com.share.my_todo.controller;

import com.share.my_todo.DTO.notice.NoticeDto;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.service.notice.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;


    @GetMapping("/list")
    public ResponseEntity<List<NoticeDto>> getNoticeList() {
        List<NoticeDto> list = noticeService.getNotReadNoticeList();

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    @GetMapping("/checkNotice")
    public int checkNotice(@AuthenticationPrincipal Member member) {
        if (member.getMemberId().isEmpty()) {
            return 0;
        } else {
            int noticeCount = noticeService.checkNotice(member);
            return noticeCount;
        }
    }

    @GetMapping("/readNotice")
    public ResponseEntity<?> readNotice() {
        noticeService.readNotice();

        return ResponseEntity.status(HttpStatus.OK).body("read success");
    }

    @GetMapping("/prev-list")
    public ResponseEntity<Page<NoticeDto>> getPrevList(@RequestParam(name = "page",defaultValue = "1")int page) {
        page = page-1;
        Page<NoticeDto> prevList = noticeService.getPrevNoticeList(page);

        return ResponseEntity.status(HttpStatus.OK).body(prevList);
    }

}
