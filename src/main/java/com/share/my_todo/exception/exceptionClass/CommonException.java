package com.share.my_todo.exception.exceptionClass;

import com.share.my_todo.exception.ErrorCode;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException{

    private ErrorCode errorCode;

    public CommonException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
