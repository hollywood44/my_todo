package com.share.my_todo.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    //global exception
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "DB_ERROR","데이터 베이스 에러가 발생했습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST,"PASSWORD_NOT_MATCH","비밀번호가 일치하지 않습니다."),
    ID_NOT_FOUND(HttpStatus.BAD_REQUEST,"ID_NOT_FOUND","아이디를 찾을 수 없습니다."),
    LOGIN_ERROR(HttpStatus.BAD_REQUEST,"ID_OR_PASSWORD_WRONG","아이디 혹은 비밀번호가 틀렸습니다."),
    ACCESS_DENIED(HttpStatus.BAD_REQUEST,"ACCESS_DENIED","권한 없는 접근입니다."),

    //sign up exception
    ID_DUPLICATED(HttpStatus.BAD_REQUEST,"ID_DUPLICATED","아이디가 중복됩니다."),


    //common posting exception
    POSTING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"POSTING_ERROR","등록에 실패했습니다."),
    POSTING_VALUE_EMPTY(HttpStatus.BAD_REQUEST,"POSTING_VALUE_DOSE_NOT_EMPTY","빈 값은 제출하실 수 없습니다."),


    //common modify exception
    MODIFY_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"MODIFY_ERROR", "수정에 실패했습니다."),
    MODIFY_VALUE_EMPTY(HttpStatus.BAD_REQUEST,"MODIFY_VALUE_DOSE_NOT_EMPTY","수정할 정보는 비어 있을 수 없습니다."),

    // todos exception
    TODO_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "TODO_NOT_FOUND", "해당 할일을 찾을 수 없습니다."),

    // about friend exception
    ALREADY_EXIST_FRIEND(HttpStatus.BAD_REQUEST,"ALREADY_EXIST_FRIEND","이미 친구목록에 있는 친구입니다."),
    CAN_FOLLOW_AFTER_TWO_DAYS(HttpStatus.BAD_REQUEST,"CAN_FOLLOW_AFTER_TWO_DAYS","상대방이 팔로우 거절시 이틀 후 자정부터 다시 팔로우 요청이 가능합니다."),

    // about board exception
    BOARD_NOT_FOUND(HttpStatus.BAD_REQUEST,"BOARD_NOT_FOUND","해당 게시물을 찾을 수  없습니다.");


    private HttpStatus status;
    private String errorCode;
    private String message;
}
