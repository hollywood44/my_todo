package com.share.my_todo.config.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.share.my_todo.exception.ErrorCode;
import com.share.my_todo.exception.ErrorResponse;
import com.share.my_todo.util.CookieUtil;
import com.share.my_todo.util.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.filter.OncePerRequestFilter;

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
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;


    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String path =  request.getServletPath();
            String token = TokenUtil.resolveToken(request);
            boolean validate = false;
            if (token != null) {
                validate = jwtTokenProvider.validateToken(token);
            }

            if (path.startsWith("/api/auth/new-token") || request.getMethod().equals("OPTIONS") || CorsUtils.isPreFlightRequest(request)) {
                chain.doFilter(request, response);
            } else if (token != null && validate) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                chain.doFilter(request, response);
            } else {
                chain.doFilter(request, response);
            }
        } catch (ExpiredJwtException e) {
            System.out.println("토큰 만료!!");
            ErrorResponse errorResponse = new ErrorResponse(ErrorCode.JWT_ACCESS_TOKEN_EXPIRED);
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            chain.doFilter(request,response);
        }
    }
}

