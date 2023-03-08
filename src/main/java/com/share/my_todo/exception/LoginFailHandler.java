package com.share.my_todo.exception;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String err = "로그인 오류 입니다.";
        if (e instanceof BadCredentialsException || e instanceof InternalAuthenticationServiceException){
            err="LOGIN_ERROR_ID_PASSWORD";
        }else if (e instanceof UsernameNotFoundException){
            err="LOGIN_ERROR_ID_NOT_PRESENT";
        }
        else{
            err="LOGIN_ERROR_UNDEFINED";
        }

        err= URLEncoder.encode(err,"UTF-8");//한글 인코딩 깨지는 문제 방지
        setDefaultFailureUrl("/member/signIn?error=true&message=" + err);
        super.onAuthenticationFailure(request,response,e);
    }
}
