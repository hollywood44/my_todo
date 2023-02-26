package com.share.my_todo.exception.controller;

import com.share.my_todo.exception.ErrorResponse;
import com.share.my_todo.exception.exceptionClass.CommonException;
import com.share.my_todo.exception.exceptionClass.IdDuplicateException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    @ExceptionHandler(IdDuplicateException.class)
    public String handleIdDuplicateException(IdDuplicateException ex, RedirectAttributes redirectAttributes) {
        log.error("IdDuplicateException", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        redirectAttributes.addFlashAttribute("error", response);
        return "redirect:/member/signUp";
    }
}
