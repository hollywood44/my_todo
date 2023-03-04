package com.share.my_todo.entity.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CommonNotice {

    SUGGEST_ANSWER("건의사항에 답변이 달렸습니다.");

    private String status;
}
