package com.share.my_todo.exception;

import lombok.Getter;

@Getter
public class ErrorResponse{

    private int status;
    private String errorCode;
    private String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.errorCode = errorCode.getErrorCode();
        this.message = errorCode.getMessage();
    }
}
