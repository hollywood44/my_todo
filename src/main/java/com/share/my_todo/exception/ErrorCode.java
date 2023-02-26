package com.share.my_todo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    COMMON_ERROR(500,"COMMON-ERR-500","It is Common Error"),
    ID_DUPLICATION(400, "SIGNUP-ID-DUP-400", "MEMBER ID DUPLICATED");

    private int status;
    private String errorCode;
    private String message;
}
