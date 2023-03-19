package com.share.my_todo.controller;

import com.share.my_todo.DTO.member.MemberDto;
import com.share.my_todo.DTO.member.MemberLoginRequestDto;
import com.share.my_todo.DTO.member.PasswordCheckDto;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.config.login.TokenInfo;
import com.share.my_todo.service.member.MemberService;
import com.share.my_todo.util.CookieUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;



    /**
     * 회원 정보 불러옴
     * @return 회원정보 반환
     */
    @GetMapping("/info")
    public ResponseEntity<MemberDto> getMemberInfo() {
        MemberDto memberInfo = memberService.getMemberInfo();

        return ResponseEntity.status(HttpStatus.OK).body(memberInfo);
    }

    /**
     * 회원 정보 수정
     * @return HttpStatus 반환
     */
    @PostMapping("/modify-info")
    public ResponseEntity<?> modifyMemberInfo(@RequestBody MemberDto modifyInfo) {
        memberService.modifyMemberInfo(modifyInfo);

        return ResponseEntity.status(HttpStatus.OK).body("info modify complete");
    }

    /**
     * 비밀번호 변경
     * @param passwordCheckDto 바꿀 비밀번호, 체크를 위한 기존 비밀번호
     * @return HttpStatus.OK 반환
     */
    @PostMapping("/modify-password")
    public ResponseEntity<?> modifyPassword(@RequestBody PasswordCheckDto passwordCheckDto) {
        memberService.modifyPassword(passwordCheckDto);

        return ResponseEntity.status(HttpStatus.OK).body("password modify complete");
    }

    /**
     * 회원 탈퇴
     * @param passwordCheckDto passwordCheck : 체크를 위한 기존 비밀번호
     * @return HttpStatus.OK 반환
     */
    @PostMapping("/delete-account")
    public ResponseEntity<?> deleteAccount(@RequestBody PasswordCheckDto passwordCheckDto) {
        memberService.deleteAccount(passwordCheckDto);

        return ResponseEntity.status(HttpStatus.OK).body("delete account complete");
    }

}
