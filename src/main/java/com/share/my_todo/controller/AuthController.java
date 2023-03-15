package com.share.my_todo.controller;

import com.share.my_todo.DTO.member.MemberLoginRequestDto;
import com.share.my_todo.config.login.TokenInfo;
import com.share.my_todo.service.member.AuthService;
import com.share.my_todo.util.CookieUtil;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 로그인
     * @param memberLoginRequestDto 아이디와 비밀번호
     * @return 로그인 성공하면 토큰 반환
     */
    @PostMapping("/sign-in")
    public ResponseEntity<?> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto, HttpServletResponse response) {
        String memberId = memberLoginRequestDto.getMemberId();
        String password = memberLoginRequestDto.getPassword();
        TokenInfo tokenInfo = authService.login(memberId, password);
        CookieUtil.setCookie(response,"refreshToken",tokenInfo.getRefreshToken(),60*1000);
        tokenInfo.setRefreshToken("");

        return ResponseEntity.status(HttpStatus.OK).body(tokenInfo);
    }


    @PostMapping("/new-token")
    public ResponseEntity<TokenInfo> requestNewToken(HttpServletRequest request, HttpServletResponse response) {
        TokenInfo newAccessToken = authService.makeNewAccessToken(request, response);

        return ResponseEntity.status(HttpStatus.OK).body(newAccessToken);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request,response,"refreshToken");

        return ResponseEntity.status(HttpStatus.OK).body("logout success");
    }

    @GetMapping("/test")
    public ResponseEntity<?> test(HttpServletRequest request, HttpServletResponse response) {
        Optional<Cookie> cookie = CookieUtil.getCookie(request, "refreshToken");
        if (cookie.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(cookie.get().getMaxAge());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(cookie.get().getMaxAge());
        }
    }
}
