package com.share.my_todo.controller;


import com.share.my_todo.DTO.board.BoardDto;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final BoardService boardService;

    @GetMapping("/suggest-answer")
    public String suggestAnswer(Model model, @RequestParam("boardId") Long boardId) {
        model.addAttribute("parentId", boardId);
        return "suggestAnswerPosting";
    }

    @PostMapping("/suggest-answer")
    public String suggestAnswerPosting(BoardDto boardDto,@AuthenticationPrincipal Member admin) {
        boardDto.setWriter(admin.getMemberId());
        boardDto.setContent(boardDto.getContent().replace("\n", "<br>"));

        boardService.answerPosting(boardDto);

        return "redirect:/board/suggest-detail?boardId=" + boardDto.getParentId();
    }



}
