package com.share.my_todo.controller;

import com.share.my_todo.DTO.member.MemberDto;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {


    private final MemberService memberService;

    @GetMapping("/signIn")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signUp")
    public String signUpPage() {
        return "singUp";
    }

    /**
     * 회원가입
     * @param signUpData 회원가입에 필요한 데이터
     * @return 회원가입 완료된 아이디
     */
    @PostMapping("/signup")
    public String signUp(MemberDto signUpData) {
        return memberService.signUp(signUpData);
    }

    /**
     * 아이디 중복확인
     * @param memberId 중복확인 할 아이디
     * @return true / false
     */
    @GetMapping("/signup/check")
    public boolean idCheck(@RequestParam("memberId")String memberId) {
        return memberService.idCheck(memberId);
    }

    /**
     * 회원정보 수정
     * @param modifyData 바뀌지 않은 데이터와 변경된 데이터
     * @return 바뀐 데이터
     */
    @PostMapping("/modify-info")
    public MemberDto modifyInfo(MemberDto modifyData) {
        return memberService.modifyMemberInfo(modifyData);
    }

    /**
     * 내 정보 보기
     * @param member 로그인된 회원정보
     * @return 내 정보
     */
    @GetMapping("/my-info")
    public MemberDto getMyInfo(@AuthenticationPrincipal Member member) {
        return memberService.getMyInfo(member.getMemberId());
    }

    /**
     * 비밀번호 변경
     * @param password 바꿀 비밀번호
     * @param member 로그인된 회원 정보
     * @return 성공 true, 실패 false
     */
    @PostMapping("/modify-password")
    public boolean modifyPassword(@RequestParam("password")String password,@AuthenticationPrincipal Member member) {
        if (!memberService.modifyPassword(member.getMemberId(), password).isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    @PostMapping("/delete-account")
    public String deleteAccount(@AuthenticationPrincipal Member member,@RequestParam("password") String password) {
        String status = memberService.deleteAccount(member.getMemberId(), password);

        return status;
    }

}
