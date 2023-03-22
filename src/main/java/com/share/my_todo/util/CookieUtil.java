package com.share.my_todo.util;

import org.springframework.http.ResponseCookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class CookieUtil {

    public static void setCookie(HttpServletResponse response,String name,String value,int maxAge) {
        ResponseCookie cookie = ResponseCookie.from(name, value)
                .maxAge(maxAge)
                .path("/")
                .secure(true)
                .sameSite("None")
                .httpOnly(true)
                .build();
        response.setHeader("Set-Cookie",cookie.toString());
    }

    public static Optional<Cookie> getCookie(HttpServletRequest request,String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return Optional.of(cookie);
                }
            }
        }
        return Optional.empty();
    }

    public static void deleteCookie(HttpServletRequest request,HttpServletResponse response,String name) {
        ResponseCookie cookie = ResponseCookie.from(name, null)
                .path("/")
                .maxAge(0)
                .build();

        response.setHeader("Set-Cookie",cookie.toString());
    }
}
