package com.share.my_todo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    COMMON_ERROR(500,"COMMON-ERR-500","It is Common Error"),
    ID_DUPLICATION(400, "SIGNUP-ID-Dup", "이미 있는 아이디 입니다."),
    LOGIN_ERROR_ID_PASSWORD(400,"Login-error-id-password","아이디 또는 비밀번호가 맞지 않습니다."),
    LOGIN_ERROR_ID_NOT_PRESENT(400,"Login-error-id","존재하지 않는 아이디 입니다."),
    LOGIN_ERROR_UNDEFINED(500,"Login-error-undefined","알 수 없는 이유로 로그인이 안되고 있습니다."),
    TODO_UPLOAD_ERROR(500,"TODO-upload-error","할일 업로드가 실패했습니다."),
    FOLLOW_REQUEST_ERROR(500, "FOLLOW-REQUEST-error", "친구 요청이 실패했습니다."),
    MODIFY_INFO_ERROR(500,"MODIFY-ERROR","정보 수정에 실패했습니다."),
    MEMBER_DELETE_FAILED_ERROR(500,"Delete-Failed","회원탈퇴에 실패 했습니다."),
    MEMBER_DELETE_PASSWORD_WRONG_ERROR(400, "Password-Different", "비밀번호가 일치하지 않습니다.");

    private int status;
    private String errorCode;
    private String message;
}
