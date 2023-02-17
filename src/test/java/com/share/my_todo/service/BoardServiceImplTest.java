package com.share.my_todo.service;

import com.share.my_todo.DTO.board.BoardDto;
import com.share.my_todo.repository.board.BoardRepository;
import com.share.my_todo.service.board.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

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
                .title("posting test 6")
                .content("post test content 6")
                .writer("member7")
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

    @Test
    void detailTest() {
        System.out.println(boardService.postDetail(4L));
    }

    @Test
    void getListTest() {
        Page<BoardDto> list = boardService.getAllBoardList(0, 10);
        for (BoardDto dto : list) {
            System.out.println(dto);
        }
    }

}