package com.share.my_todo.util;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class TokenUtil {

    // Request Header 에서 토큰 정보 추출
    public static String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
