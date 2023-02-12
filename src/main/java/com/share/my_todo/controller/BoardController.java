package com.share.my_todo.controller;

import com.share.my_todo.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public String suggestPageMain(Model model, @RequestParam(name = "page",defaultValue = "1")int page) {
        model.addAttribute("boardList",boardService.getAllBoardList(page,10));
        model.addAttribute("maxPage", 10);
        return "suggestBoard";
    }
}
