package com.share.my_todo.service;

import com.share.my_todo.DTO.board.BoardDto;
import com.share.my_todo.entity.board.Board;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.repository.board.BoardRepository;
import com.share.my_todo.service.board.BoardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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


        IntStream.rangeClosed(0,60).forEach(i -> boardRepository.save(Board.builder()
                .title("test" + i)
                .writer(Member.builder().memberId("test01").build())
                .content("test")
                .answer(false)
                .build()));
    }

    @Test
    @Rollback(value = false)
    void modifyTest() {

    }

    @Test
    void detailTest() {

    }

    @Test
    void getListTest() {
        Page<BoardDto> list = boardService.getAllBoardList(0, 10);
        for (BoardDto dto : list) {
            System.out.println(dto);
        }
    }

}