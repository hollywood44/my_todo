package com.share.my_todo.service;

import com.share.my_todo.DTO.board.BoardDto;
import com.share.my_todo.entity.board.Board;
import com.share.my_todo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;

    @Override
    public Long boardPosting(BoardDto dto) {
        Board post = dtoToEntity(dto);

        return boardRepository.save(post).getBoardId();
    }

    @Override
    public Long modifyPost(BoardDto dto) {
        Board modifiedPost = boardRepository.findById(dto.getBoardId()).get();
        modifiedPost.modifyPost(dto);

        return boardRepository.save(modifiedPost).getBoardId();
    }

    @Override
    public BoardDto postDetail(Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        BoardDto detailBoard = entityToDto(board);

        return detailBoard;
    }
}
