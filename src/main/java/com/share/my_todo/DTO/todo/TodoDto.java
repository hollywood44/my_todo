package com.share.my_todo.DTO.todo;

import com.share.my_todo.entity.common.TodoProgress;
import com.share.my_todo.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TodoDto {

    private Long todoId;
    private String todo;
    private LocalDateTime finishDate;
    private TodoProgress progress;
    private String memberId;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

}
