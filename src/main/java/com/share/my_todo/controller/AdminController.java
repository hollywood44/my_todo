package com.share.my_todo.controller;


import com.share.my_todo.DTO.board.BoardDto;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.service.board.BoardService;
import com.share.my_todo.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final BoardService boardService;
    private final MemberService memberService;

    @PostMapping("/suggest-answer")
    public ResponseEntity<?> suggestAnswerPosting(@RequestBody BoardDto boardDto) {
        boardService.answerPosting(boardDto);

        return ResponseEntity.status(HttpStatus.OK).body(boardDto.getParentId() + " 번 게시물에 대한 답변이 정상적으로 작성 되었습니다.");
    }



}
