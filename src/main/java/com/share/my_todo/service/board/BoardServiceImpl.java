package com.share.my_todo.service.board;

import com.share.my_todo.DTO.board.BoardDto;
import com.share.my_todo.entity.board.Board;
import com.share.my_todo.entity.common.CommonNotice;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.entity.notice.Notice;
import com.share.my_todo.repository.board.BoardRepository;
import com.share.my_todo.repository.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{

    private final BoardRepository boardRepository;
    private final NoticeRepository noticeRepository;

    @Override
    public Long boardPosting(BoardDto dto) {
        dto.setAnswer(false);
        Board post = dtoToEntity(dto);

        return boardRepository.save(post).getBoardId();
    }

    @Override
    @Transactional
    public Long answerPosting(BoardDto dto) {
        List<Board> saveList = new ArrayList<>();

        Board parentBoard = boardRepository.findById(dto.getParentId()).get();
        parentBoard.setAnswer(true);
        saveList.add(parentBoard);

        Board answer = dtoToEntity(dto);
        saveList.add(answer);

        boardRepository.saveAll(saveList);

        Notice notice = Notice.builder().member(Member.builder().memberId(dto.getWriter()).build()).notice(CommonNotice.SUGGEST_ANSWER.getStatus()).build();
        noticeRepository.save(notice);

        return dto.getParentId();
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

        if (board.isAnswer()) {
            detailBoard.setAnswerBoard(entityToDto(boardRepository.findByParentId(boardId)));
        }
//todo 답변 있으면 답변도 같이 리턴
        return detailBoard;
    }

    @Override
    public Page<BoardDto> getAllBoardList(int page,int size) {
        Sort sort = Sort.by("boardId").descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Board> entityList = boardRepository.findAllByParentIdIsNull(pageable);
        Page<BoardDto> boardList = entityList.map(entity -> entityToDtoForList(entity));

        return boardList;
    }

    @Override
    @Transactional
    public Long deletePost(Long boardId){
        try {
            List<Board> deleteList = new ArrayList<>();
            Board board = boardRepository.findById(boardId).get();
            deleteList.add(board);
            if (board.isAnswer()) {
                Board child = boardRepository.findByParentId(boardId);
                deleteList.add(child);
            }
            boardRepository.deleteAll(deleteList);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("게시물 삭제 중 오류 발생");
        }
        return boardId;
    }

}
