package com.share.my_todo.entity.board;

import com.share.my_todo.entity.common.CommonTime;
import com.share.my_todo.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

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

    private String title;
    private String content;

    @ManyToOne(targetEntity = Member.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "writer")
    private Member writer;
}
