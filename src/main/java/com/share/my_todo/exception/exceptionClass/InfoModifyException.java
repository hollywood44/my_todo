package com.share.my_todo.exception.exceptionClass;

import com.share.my_todo.exception.ErrorCode;
import lombok.Getter;

@Getter
public class InfoModifyException extends RuntimeException{

    private ErrorCode errorCode;

    public InfoModifyException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
