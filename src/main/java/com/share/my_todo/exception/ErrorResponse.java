package com.share.my_todo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse{

    private HttpStatus status;
    private String errorCode;
    private String message;

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus();
        this.errorCode = errorCode.getErrorCode();
        this.message = errorCode.getMessage();
    }
}
