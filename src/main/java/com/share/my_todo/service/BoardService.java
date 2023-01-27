package com.share.my_todo.service;

import com.share.my_todo.DTO.board.BoardDto;
import com.share.my_todo.entity.board.Board;
import com.share.my_todo.entity.member.Member;

public interface BoardService {

    default BoardDto entityToDto(Board entity) {
        BoardDto dto = BoardDto.builder()
                .boardId(entity.getBoardId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter().getMemberId())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
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
}
