package com.share.my_todo.controller;


import com.share.my_todo.DTO.board.BoardDto;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.service.board.BoardService;
import com.share.my_todo.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/test")
    public String test() {
        return "has role admin";
    }




    @GetMapping("/suggest-answer")
    public String suggestAnswer(Model model, @RequestParam("boardId") Long boardId) {
        model.addAttribute("parentId", boardId);
        return "suggestBoard/suggestAnswerPosting";
    }

    @PostMapping("/suggest-answer")
    public String suggestAnswerPosting(BoardDto boardDto,@AuthenticationPrincipal Member admin) {
        boardDto.setWriter(admin.getMemberId());
        boardDto.setContent(boardDto.getContent().replace("\n", "<br>"));

        boardService.answerPosting(boardDto);

        return "redirect:/board/suggest-detail?boardId=" + boardDto.getParentId();
    }



}
