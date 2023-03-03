package com.share.my_todo.exception.exceptionClass;

import com.share.my_todo.exception.ErrorCode;
import lombok.Getter;

@Getter
public class AccountDeleteException extends RuntimeException{

    private ErrorCode errorCode;

    public AccountDeleteException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
