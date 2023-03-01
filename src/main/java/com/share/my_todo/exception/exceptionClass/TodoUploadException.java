package com.share.my_todo.exception.exceptionClass;

import com.share.my_todo.exception.ErrorCode;
import lombok.Getter;

@Getter
public class TodoUploadException extends RuntimeException{

    private ErrorCode errorCode;

    public TodoUploadException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
