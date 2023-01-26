package com.share.my_todo.entity.todo;

import com.share.my_todo.entity.common.CommonTime;
import com.share.my_todo.entity.common.TodoProgress;
import com.share.my_todo.entity.member.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table(name = "todo_tbl")
public class Todo extends CommonTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    private String todo;

    private LocalDateTime finishDate;

    @Enumerated(EnumType.STRING)
    private TodoProgress progress;

    @ManyToOne(targetEntity = Member.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

}
