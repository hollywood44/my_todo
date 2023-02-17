package com.share.my_todo.DTO.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDto {

    private Long boardId;
    private String title;
    private String content;
    private Long parentId;
    private boolean answer;
    private String writer;
    private String regDate;
    private String modDate;

    private BoardDto answerBoard;

}
