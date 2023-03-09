package com.share.my_todo.exception.controller;

import com.share.my_todo.exception.ErrorResponse;
import com.share.my_todo.exception.exceptionClass.*;
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

    @ExceptionHandler(CommonException.class)
    public String handleCommonException(CommonException ex, RedirectAttributes redirectAttributes,HttpServletRequest request) {
        log.error(ex.getMessage(), ex);
        String requestPath = request.getRequestURI();

        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        redirectAttributes.addFlashAttribute("error", response);

        switch (ex.getErrorCode()) {
            case MEMBER_DELETE_PASSWORD_WRONG_ERROR : return "redirect:/member/my-info/info";
            case MODIFY_INFO_NOT_EMPTY : return "redirect:/member/my-info/info";
            case POST_VALUE_NOT_EMPTY : if(requestPath.contains("todo")) return "redirect:/todo/main";
            default: return "redirect:/todo/main";
        }
    }

    @ExceptionHandler(AccountDeleteException.class)
    public String handleIdDuplicateException(AccountDeleteException ex, RedirectAttributes redirectAttributes) {
        log.error(ex.getMessage(), ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        redirectAttributes.addFlashAttribute("error", response);

        return "redirect:/todo/main";
    }

    @ExceptionHandler(IdDuplicateException.class)
    public String handleIdDuplicateException(IdDuplicateException ex, RedirectAttributes redirectAttributes,HttpServletRequest request) {
        log.error("IdDuplicateException", ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        redirectAttributes.addFlashAttribute("error", response);

        return "redirect:" + request.getRequestURI();
    }

    @ExceptionHandler(FollowRequestException.class)
    public String handleFollowRequestException(FollowRequestException ex, RedirectAttributes redirectAttributes) {
        log.error("FollowRequestException",ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        redirectAttributes.addFlashAttribute("error", response);

        return "redirect:/friend/list";
    }

    @ExceptionHandler(TodoUploadException.class)
    public String handleFollowRequestException(TodoUploadException ex, RedirectAttributes redirectAttributes) {
        log.error("TodoUploadException",ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        redirectAttributes.addFlashAttribute("error", response);

        return "redirect:/todo/main";
    }

    @ExceptionHandler(InfoModifyException.class)
    public String handleInfoModifyException(InfoModifyException ex, RedirectAttributes redirectAttributes) {
        log.error("InfoModifyException",ex);
        ErrorResponse response = new ErrorResponse(ex.getErrorCode());
        redirectAttributes.addFlashAttribute("error", response);

        return "redirect:/member/my-info/info";
    }

}
