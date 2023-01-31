package com.share.my_todo.entity.todo;

import com.share.my_todo.DTO.todo.TodoDto;
import com.share.my_todo.entity.common.CommonTime;
import com.share.my_todo.entity.common.TodoProgress;
import com.share.my_todo.entity.member.Member;
import javax.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 할일 아이디, 할일, 목표날짜, 진행도, 회원
 */
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

    @Column(nullable = false)
    private String todo;

    @Column(nullable = false)
    private LocalDate finishDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TodoProgress progress;

    @ManyToOne(targetEntity = Member.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    public void modifyTodo(TodoDto dto) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate changeDate = LocalDate.parse(dto.getFinishDate(), format);

        this.todo = dto.getTodo();
        this.finishDate = changeDate;
    }

    public void progressChangeToComplete() {
        this.progress = TodoProgress.Success;
    }
}
