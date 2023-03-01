package com.share.my_todo.exception.controller;

import com.share.my_todo.exception.ErrorResponse;
import com.share.my_todo.exception.exceptionClass.CommonException;
import com.share.my_todo.exception.exceptionClass.FollowRequestException;
import com.share.my_todo.exception.exceptionClass.IdDuplicateException;
import com.share.my_todo.exception.exceptionClass.TodoUploadException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(IdDuplicateException.class)
    public String handleIdDuplicateException(IdDuplicateException ex, RedirectAttributes redirectAttributes,HttpServletRequest request) {
        log.error("IdDuplicateException", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        redirectAttributes.addFlashAttribute("error", response);

        return "redirect:" + request.getRequestURI();
    }

    @ExceptionHandler(FollowRequestException.class)
    public String handleFollowRequestException(FollowRequestException ex, RedirectAttributes redirectAttributes,HttpServletRequest request) {
        log.error("FollowRequestException",ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        redirectAttributes.addFlashAttribute("error", response);

        return "redirect:" + request.getRequestURI();
    }

    @ExceptionHandler(TodoUploadException.class)
    public String handleFollowRequestException(TodoUploadException ex, RedirectAttributes redirectAttributes,HttpServletRequest request) {
        log.error("TodoUploadException",ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        redirectAttributes.addFlashAttribute("error", response);

        return "redirect:" + request.getRequestURI();
    }

}
