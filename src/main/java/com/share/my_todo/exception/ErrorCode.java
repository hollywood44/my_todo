package com.share.my_todo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    COMMON_ERROR(500,"COMMON-ERR-500","It is Common Error"),
    ID_DUPLICATION(400, "SIGNUP-ID-Dup", "이미 있는 아이디 입니다."),
    TODO_UPLOAD_ERROR(500,"TODO-upload-error","할일 업로드가 실패했습니다."),
    FOLLOW_REQUEST_ERROR(500, "FOLLOW-REQUEST-error", "친구 요청이 실패했습니다.");

    private int status;
    private String errorCode;
    private String message;
}
