package com.share.my_todo.controller;

import com.share.my_todo.DTO.member.MemberDto;
import com.share.my_todo.DTO.member.MemberLoginRequestDto;
import com.share.my_todo.DTO.member.PasswordCheckDto;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.config.login.TokenInfo;
import com.share.my_todo.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/test")
    public String test(){
        return "has role!";
    }

    /**
     * 로그인
     * @param memberLoginRequestDto 아이디와 비밀번호
     * @return 로그인 성공하면 토큰 반환
     */
    @PostMapping("/sign-in")
    public TokenInfo login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        String memberId = memberLoginRequestDto.getMemberId();
        String password = memberLoginRequestDto.getPassword();
        TokenInfo tokenInfo = memberService.login(memberId, password);
        return tokenInfo;
    }


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
     * @param memberId 중복확인 할 아이디
     * @return true / false
     */
    @PostMapping("/signup/check")
    public boolean idCheck(@RequestBody String memberId) {
        return memberService.idCheck(memberId);
    }

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

    @PostMapping("/modify-password")
    public ResponseEntity<?> modifyPassword(@RequestBody PasswordCheckDto passwordCheckDto) {
        memberService.modifyPassword(passwordCheckDto);

        return ResponseEntity.status(HttpStatus.OK).body("password modify complete");
    }


    //--------위는 바뀐 메서드--------//
    //--------아래는 안바뀐 메서드--------//


    /**
     * 회원 탈퇴
     * @param member
     * @param password
     * @return
     */
    @PostMapping("/delete-account")
    public String deleteAccount(@AuthenticationPrincipal Member member,@RequestParam("password") String password) {
        memberService.deleteAccount(member.getMemberId(), password);

        return "redirect:/member/signIn";
    }

}
