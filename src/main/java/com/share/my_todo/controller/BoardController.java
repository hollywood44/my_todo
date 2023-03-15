package com.share.my_todo.controller;

import com.share.my_todo.DTO.board.BoardDto;
import com.share.my_todo.entity.common.BoardDetailStatus;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.service.board.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public ResponseEntity<Page<BoardDto>> getAllBoardList(@RequestParam(name = "page", defaultValue = "1") int page) {
        page = page - 1;
        Page<BoardDto> boardList = boardService.getAllBoardList(page, 10);

        return ResponseEntity.status(HttpStatus.OK).body(boardList);
    }

    @GetMapping("/detail/{boardId}")
    public ResponseEntity<BoardDto> getBoardDetail(@PathVariable Long boardId) {
        BoardDto detail = boardService.getPostDetail(boardId, BoardDetailStatus.DETAIL);

        return ResponseEntity.status(HttpStatus.OK).body(detail);
    }

    @GetMapping("/detail/modify/{boardId}")
    public ResponseEntity<BoardDto> suggestModifyPage(@PathVariable Long boardId) {
        BoardDto board = boardService.getPostDetail(boardId, BoardDetailStatus.MODIFY);

        board.setContent(board.getContent().replace("<br>", "\n"));

        return ResponseEntity.status(HttpStatus.OK).body(board);
    }

    @PostMapping("/posting")
    public ResponseEntity<?> suggestPosting(BoardDto boardDto) {
        boardService.boardPosting(boardDto);

        return ResponseEntity.status(HttpStatus.OK).body("posting success");
    }

    @PostMapping("/modify")
    public ResponseEntity<?> suggestModify(BoardDto boardDto) {
        boardService.modifyPost(boardDto);

        return ResponseEntity.status(HttpStatus.OK).body("modify success");
    }

    @PostMapping("/delete")
    public String deletePost(@RequestBody Map<String,Long> boardMap) {
        boardService.deletePost(boardMap.get("boardId"));

        return "redirect:/board";
    }


}
