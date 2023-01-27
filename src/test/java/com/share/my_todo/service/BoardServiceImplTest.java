package com.share.my_todo.service;

import com.share.my_todo.DTO.board.BoardDto;
import com.share.my_todo.repository.BoardRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceImplTest {

    @Autowired
    BoardRepository boardRepository;
    @Autowired
    BoardService boardService;

    @Test
    @Rollback(value = false)
    void postingTest() {
        BoardDto dto = BoardDto.builder()
                .title("posting test 1")
                .content("post test content 1")
                .writer("member1")
                .build();
        System.out.println(boardService.boardPosting(dto));
    }

    @Test
    @Rollback(value = false)
    void modifyTest() {
        BoardDto dto = BoardDto.builder()
                .boardId(2L)
                .title("posting test 1 - modify")
                .content("post test content 1  - modify")
                .writer("member1")
                .build();
        System.out.println(boardService.modifyPost(dto));
    }

}