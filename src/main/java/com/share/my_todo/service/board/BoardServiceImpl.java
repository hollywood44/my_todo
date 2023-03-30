package com.share.my_todo.service.board;

import com.share.my_todo.DTO.board.BoardDto;
import com.share.my_todo.repository.member.MemberRepository;
import com.share.my_todo.util.SecurityUtil;
import com.share.my_todo.entity.board.Board;
import com.share.my_todo.entity.common.Auth;
import com.share.my_todo.entity.common.BoardDetailStatus;
import com.share.my_todo.entity.common.CommonNotice;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.entity.notice.Notice;
import com.share.my_todo.exception.ErrorCode;
import com.share.my_todo.exception.exceptionClass.CommonException;
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
    private final MemberRepository memberRepository;

    @Override
    public void boardPosting(BoardDto dto) {
        if (dto.getTitle() == null || dto.getTitle().isEmpty()) {
            throw new CommonException(ErrorCode.POSTING_VALUE_EMPTY);
        }
        if (dto.getContent() == null || dto.getContent().isEmpty()) {
            throw new CommonException(ErrorCode.POSTING_VALUE_EMPTY);
        }
        dto.setWriter(SecurityUtil.getCurrentMemberId());
        dto.setContent(dto.getContent().replace("\n", "<br>"));
        dto.setAnswer(false);

        Board post = dtoToEntity(dto);

        boardRepository.save(post);
    }

    @Override
    @Transactional
    public void answerPosting(BoardDto dto) {
        dto.setContent(dto.getContent().replace("\n", "<br>"));
        dto.setWriter(SecurityUtil.getCurrentMemberId());

        List<Board> saveList = new ArrayList<>();

        Board parentBoard = boardRepository.findById(dto.getParentId()).get();
        parentBoard.setAnswer(true);
        saveList.add(parentBoard);

        Board answer = dtoToEntity(dto);
        saveList.add(answer);

        boardRepository.saveAll(saveList);

        Notice notice = Notice.builder().member(Member.builder().memberId(dto.getWriter()).build()).notice(CommonNotice.SUGGEST_ANSWER.getStatus()).build();
        noticeRepository.save(notice);
    }

    @Override
    public void modifyPost(BoardDto dto) {
        if (dto.getTitle() == null || dto.getTitle().isEmpty()) {
            throw new CommonException(ErrorCode.POSTING_VALUE_EMPTY);
        }
        if (dto.getContent() == null || dto.getContent().isEmpty()) {
            throw new CommonException(ErrorCode.POSTING_VALUE_EMPTY);
        }

        dto.setContent(dto.getContent().replace("\n", "<br>"));

        Board modifiedPost = boardRepository.findById(dto.getBoardId()).get();
        modifiedPost.modifyPost(dto);

        boardRepository.save(modifiedPost);
    }

    @Override
    public BoardDto getPostDetail(Long boardId, BoardDetailStatus status) {
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new CommonException(ErrorCode.BOARD_NOT_FOUND));

        if (status.equals(BoardDetailStatus.MODIFY)) {
            if (!board.getWriter().getMemberId().equals(SecurityUtil.getCurrentMemberId())) {
                throw new CommonException(ErrorCode.ACCESS_DENIED);
            }
        }

        BoardDto detailBoard = entityToDto(board);

        if (board.isAnswer()) {
            detailBoard.setAnswerBoard(entityToDto(boardRepository.findByParentId(boardId)));
        }

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
    public void deletePost(Long boardId) {
        Board board = boardRepository.findById(boardId).get();
        Member isAdmin = memberRepository.findById(SecurityUtil.getCurrentMemberId()).get();

        if (isAdmin.getAuth() == Auth.ADMIN ||
                board.getWriter().getMemberId().equals(SecurityUtil.getCurrentMemberId())) {
            List<Board> deleteList = new ArrayList<>();
            deleteList.add(board);

            if (board.isAnswer()) {
                Board child = boardRepository.findByParentId(boardId);
                deleteList.add(child);
                boardRepository.deleteAll(deleteList);
            }else{
                boardRepository.deleteAll(deleteList);
            }
        } else {
            throw new CommonException(ErrorCode.ACCESS_DENIED);
        }
    }

}
