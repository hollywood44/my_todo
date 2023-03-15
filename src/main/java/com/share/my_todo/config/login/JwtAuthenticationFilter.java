package com.share.my_todo.config.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.share.my_todo.exception.ErrorCode;
import com.share.my_todo.exception.ErrorResponse;
import com.share.my_todo.util.CookieUtil;
import com.share.my_todo.util.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        // 1. Request Header 에서 JWT 토큰 추출
        String token = TokenUtil.resolveToken((HttpServletRequest) request);
        String path = ((HttpServletRequest) request).getServletPath();

        // 2. validateToken 으로 토큰 유효성 검사
        try {
            if (path.startsWith("/api/members/new-token")) {
                chain.doFilter(request, response);
            } else if (token != null && jwtTokenProvider.validateToken(token)) {
                // 토큰이 유효할 경우 토큰에서 Authentication 객체를 가지고 와서 SecurityContext 에 저장
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                chain.doFilter(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } catch (ExpiredJwtException e) {
            ErrorResponse errorResponse = new ErrorResponse(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED);
            ((HttpServletResponse) response).setCharacterEncoding("UTF-8");
            ((HttpServletResponse) response).setStatus(401);
            ((HttpServletResponse) response).getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
            ((HttpServletResponse) response).getWriter().flush();
        }

    }
}