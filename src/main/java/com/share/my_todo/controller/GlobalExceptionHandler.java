package com.share.my_todo.controller;

import com.share.my_todo.exception.ErrorCode;
import com.share.my_todo.exception.ErrorResponse;
import com.share.my_todo.exception.exceptionClass.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(CommonException.class)
    public ResponseEntity<?> handleCommonException(CommonException ex) {
        log.error(ex.getMessage(), ex.getErrorCode().getErrorCode());
        ErrorResponse response = ex.getErrorResponse();

        return ResponseEntity.status(response.getStatus())
                .body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> handleBadCredentialsException(BadCredentialsException ex){
        log.error(ex.getCause());
        log.error(ex.getMessage());

        ErrorResponse response = new ErrorResponse(ErrorCode.LOGIN_ERROR);

        return ResponseEntity.status(response.getStatus())
                .body(response);
    }
}
