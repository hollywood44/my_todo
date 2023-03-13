package com.share.my_todo.exception.exceptionClass;

import com.share.my_todo.exception.ErrorCode;
import com.share.my_todo.exception.ErrorResponse;
import lombok.Getter;

@Getter
public class CommonException extends RuntimeException{

    private ErrorCode errorCode;
    private ErrorResponse errorResponse;

    public CommonException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.errorResponse = new ErrorResponse(errorCode);
    }
}
