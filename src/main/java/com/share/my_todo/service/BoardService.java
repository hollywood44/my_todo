package com.share.my_todo.service;

import com.share.my_todo.DTO.board.BoardDto;
import com.share.my_todo.entity.board.Board;
import com.share.my_todo.entity.member.Member;
import org.springframework.data.domain.Page;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface BoardService {

    default BoardDto entityToDto(Board entity) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        BoardDto dto = BoardDto.builder()
                .boardId(entity.getBoardId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter().getMemberId())
                .regDate(entity.getRegDate().format(format))
                .modDate(entity.getModDate().format(format))
                .build();
        return dto;
    }

    default BoardDto entityToDtoForList(Board entity) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        BoardDto dto = BoardDto.builder()
                .boardId(entity.getBoardId())
                .title(entity.getTitle())
                .writer(entity.getWriter().getMemberId())
                .modDate(entity.getModDate().format(format))
                .build();
        return dto;
    }

    default Board dtoToEntity(BoardDto dto) {
        Board entity = Board.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(Member.builder().memberId(dto.getWriter()).build())
                .build();
        return entity;
    }

    /**
     * 게시글 작성
     * @param dto 제목, 내용, 글쓴이 가 포함되어 있는 dto
     * @return Long타입의 작성이 완료된 글의 게시글 번호
     */
    Long boardPosting(BoardDto dto);

    /**
     * 게시글 수정
     * @param dto 수정할 {제목 or 내용}과 글쓴이,게시글 번호가 포함되어 있는 dto
     * @return Long타입의 작성이 완료된 글의 게시글 번호
     */
    Long modifyPost(BoardDto dto);

    /**
     * 게시글 상세보기
     * @param boardId 상세하게 보고자하는 게시글 아이디
     * @return 제목,내용,작성자,등록일,수정일이 담긴 dto리턴
     */
    BoardDto postDetail(Long boardId);

    /**
     * 페이지 번호와 사이즈를 받아서 페이징된 게시물 목록을 반환
     * @param page 페이지 번호(1페이지, 2페이지...)
     * @param size 한 페이지에 게시물을 몇개씩 보여 줄 지(10 -> 10개씩 보여주겠다)
     * @return 페이징이 된 게시물 목록
     */
    Page<BoardDto> getAllBoardList(int page,int size);

    /**
     * 게시물번호를 가지고 게시물 삭제
     * @param boardId 게시물 번호
     * @return 삭제된 게시물 번호
     */
    Long deletePost(Long boardId);
}
