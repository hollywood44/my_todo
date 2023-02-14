package com.share.my_todo.entity.board;

import com.share.my_todo.DTO.board.BoardDto;
import com.share.my_todo.entity.common.CommonTime;
import com.share.my_todo.entity.member.Member;
import lombok.*;

import javax.persistence.*;

/**
 * 멤버 : 게시글아이디, 제목, 내용, 글쓴이
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table(name = "board_tbl")
public class Board extends CommonTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private Long parentId;

    private boolean answer;

    @ManyToOne(targetEntity = Member.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "writer")
    private Member writer;

    public void modifyPost(BoardDto dto) {
        this.title = dto.getTitle();
        this.content = dto.getContent();
    }

    public void setAnswer(boolean status) {
        this.answer = status;
    }
}
