package com.share.my_todo.service;

import com.share.my_todo.DTO.board.BoardDto;
import com.share.my_todo.entity.board.Board;
import com.share.my_todo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    @Override
    public Page<BoardDto> getAllBoardList(int page,int size) {
        Sort sort = Sort.by("boardId").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Board> entityList = boardRepository.findAll(pageable);
        Page<BoardDto> boardList = entityList.map(entity -> entityToDtoForList(entity));

        return boardList;
    }

    @Override
    @Transactional
    public Long deletePost(Long boardId){
        try {
            boardRepository.deleteById(boardId);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("게시물 삭제 중 오류 발생");
        }
        return boardId;
    }

}
