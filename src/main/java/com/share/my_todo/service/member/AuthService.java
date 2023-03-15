package com.share.my_todo.service.member;

import com.share.my_todo.config.login.JwtTokenProvider;
import com.share.my_todo.config.login.TokenInfo;
import com.share.my_todo.entity.member.RefreshToken;
import com.share.my_todo.exception.ErrorCode;
import com.share.my_todo.exception.exceptionClass.CommonException;
import com.share.my_todo.repository.member.RefreshTokenRepository;
import com.share.my_todo.util.CookieUtil;
import com.share.my_todo.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final RefreshTokenRepository tokenRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;


    public TokenInfo login(String memberId, String password) throws BadCredentialsException {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(memberId, password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        TokenInfo tokenInfo = jwtTokenProvider.generateToken(authentication);

        RefreshToken refreshToken = RefreshToken.builder().memberId(memberId).refreshToken(tokenInfo.getRefreshToken()).build();
        tokenRepository.save(refreshToken);

        return tokenInfo;
    }

    public void logout(HttpServletRequest request) {
        String accessToken = TokenUtil.resolveToken(request);
        String memberId = jwtTokenProvider.getSubject(accessToken);


    }


    public TokenInfo makeNewAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String accessToken = TokenUtil.resolveToken(request);
        String memberId = jwtTokenProvider.getSubject(accessToken);

        RefreshToken refreshToken = tokenRepository.findById(memberId).orElseThrow(() -> new CommonException(ErrorCode.JWT_REFRESH_TOKEN_NOT_FOUND));

        Optional<Cookie> refreshTokenCookie = CookieUtil.getCookie(request, "refreshToken");

        if (refreshTokenCookie.isEmpty()) {
            throw new CommonException(ErrorCode.JWT_REFRESH_TOKEN_NOT_FOUND);
        } else {
            if (!refreshToken.getRefreshToken().equals(refreshTokenCookie.get().getValue())) {
                throw new CommonException(ErrorCode.JWT_REFRESH_TOKEN_NOT_MATCH);
            }
        }

        boolean isRefreshTokenValid = jwtTokenProvider.validateToken(refreshToken.getRefreshToken());

        if (isRefreshTokenValid) {
            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
            TokenInfo newAccessToken = jwtTokenProvider.makeNewAccessToken(authentication);

            return newAccessToken;
        } else {
            throw new CommonException(ErrorCode.JWT_REFRESH_TOKEN_EXPIRED);
        }
    }

}
