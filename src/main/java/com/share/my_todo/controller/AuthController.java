package com.share.my_todo.controller;

import com.share.my_todo.DTO.member.MemberDto;
import com.share.my_todo.DTO.member.MemberLoginRequestDto;
import com.share.my_todo.config.login.JwtTokenProvider;
import com.share.my_todo.config.login.TokenInfo;
import com.share.my_todo.entity.board.Board;
import com.share.my_todo.exception.ErrorCode;
import com.share.my_todo.exception.exceptionClass.CommonException;
import com.share.my_todo.service.member.AuthService;
import com.share.my_todo.service.member.MemberService;
import com.share.my_todo.util.CookieUtil;
import com.share.my_todo.util.TokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;

    /**
     * 회원가입
     * @param signUpData 아이디,이름,비밀번호
     * @return 회원가입 완료된 아이디
     */
    @PostMapping("/sign-up")
    public ResponseEntity<String> signUp(@RequestBody MemberDto signUpData) {
        String signUpID = memberService.signUp(signUpData);
        return ResponseEntity.status(HttpStatus.OK)
                .body(signUpID);
    }

    /**
     * 아이디 중복확인
     * @param
     * @return true / false
     */
    @GetMapping("/signup/check")
    public ResponseEntity<Boolean> idCheck(@RequestParam String memberId) {
        Boolean check = memberService.idCheck(memberId);
        return ResponseEntity.status(HttpStatus.OK).body(check);
    }

    /**
     * 로그인
     * @param memberLoginRequestDto 아이디와 비밀번호
     * @return 로그인 성공하면 토큰 반환
     */
    @PostMapping("/sign-in")
    public ResponseEntity<?> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto, HttpServletResponse response) {
        String memberId = memberLoginRequestDto.getMemberId();
        String password = memberLoginRequestDto.getPassword();
        TokenInfo tokenInfo = authService.login(memberId, password,response);

        return ResponseEntity.status(HttpStatus.OK).body(tokenInfo);
    }

    @GetMapping("/userinfo")
    public ResponseEntity<?> getUserInfoByToken(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = TokenUtil.resolveToken(request);
        String memberId = jwtTokenProvider.getSubject(accessToken);

        return ResponseEntity.status(HttpStatus.OK).body(memberId);
    }


    @PostMapping("/new-token")
    public ResponseEntity<TokenInfo> requestNewToken(HttpServletRequest request, HttpServletResponse response) {
        TokenInfo newToken = authService.makeNewToken(request, response);

        return ResponseEntity.status(HttpStatus.OK).body(newToken);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request,response,"refreshToken");

        return ResponseEntity.status(HttpStatus.OK).body("logout success");
    }

    @GetMapping("/test")
    public String test(HttpServletRequest request, HttpServletResponse response) {
        return "test!";
    }

    @GetMapping("/test1")
    public ResponseEntity<?> test1(HttpServletRequest request, HttpServletResponse response) {
        Optional<Cookie> refreshTokenCookie = CookieUtil.getCookie(request, "refreshToken");
        if (refreshTokenCookie.isPresent()) {
            try {
                jwtTokenProvider.validateToken(refreshTokenCookie.get().getValue());
            } catch (ExpiredJwtException ex) {
                throw new CommonException(ErrorCode.JWT_REFRESH_TOKEN_EXPIRED);
            }
            return ResponseEntity.status(HttpStatus.OK).body(refreshTokenCookie.get().getValue());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("fail");
        }
    }
}
