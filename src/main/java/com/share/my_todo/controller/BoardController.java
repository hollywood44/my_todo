package com.share.my_todo.controller;

import com.share.my_todo.DTO.board.BoardDto;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public String suggestPageMain(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
        page = page - 1;
        model.addAttribute("boardList", boardService.getAllBoardList(page, 10));
        model.addAttribute("maxPage", 10);
        return "suggestBoard";
    }

    @GetMapping("/suggest-posting")
    public String suggestPostingPage() {
        return "boardPosting";
    }

    @GetMapping("/suggest-detail")
    public String suggestPostingDetailPage(@RequestParam("boardId") Long boardId, Model model) {
        model.addAttribute("detail", boardService.postDetail(boardId));

        return "boardDetail";
    }

    @GetMapping("/suggest-modify")
    public String suggestModifyPage(Model model, @RequestParam("boardId")Long boardId, @AuthenticationPrincipal Member member, RedirectAttributes redirectAttributes) {
        BoardDto board = boardService.postDetail(boardId);

        if (member.getMemberId().equals(board.getWriter())) {
            board.setContent(board.getContent().replace("<br>", "\n"));
            model.addAttribute("board", boardService.postDetail(boardId));
            return "boardModify";
        } else {
            redirectAttributes.addFlashAttribute("msg", "권한없는 접근입니다!");
            return "redirect:/board";
        }
    }

    @PostMapping("/suggest-posting")
    public String suggestPosting(BoardDto boardDto, @AuthenticationPrincipal Member member) {
        boardDto.setWriter(member.getMemberId());
        boardDto.setContent(boardDto.getContent().replace("\n", "<br>"));
        boardService.boardPosting(boardDto);

        return "redirect:/board";
    }

    @PostMapping("/suggest-modify")
    public String suggestModify(BoardDto boardDto) {
        boardDto.setContent(boardDto.getContent().replace("\n", "<br>"));
        boardService.modifyPost(boardDto);

        return "redirect:/board";
    }

    @PostMapping("/delete")
    public String deletePost(@RequestParam("boardId")Long boardId) {
        boardService.deletePost(boardId);

        return "redirect:/board";
    }
}
