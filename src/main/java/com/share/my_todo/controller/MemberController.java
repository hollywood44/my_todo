package com.share.my_todo.controller;

import com.share.my_todo.DTO.member.MemberDto;
import com.share.my_todo.DTO.member.MemberLoginRequestDto;
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
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/test")
    public String test(){
        return "has role!";
    }


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
    public ResponseEntity<?> signUp(@RequestBody MemberDto signUpData) {
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

    //--------위는 바뀐 메서드--------//
    //--------아래는 안바뀐 메서드--------//




    @GetMapping("/my-info/{menu}")
    public String myInfoPage(@AuthenticationPrincipal Member member,Model model,@PathVariable String menu) {
        if (menu.equals("help")) {
            model.addAttribute("helpImg", "uri");
            return "member/help";
        }else {
            model.addAttribute("member", memberService.getMyInfo(member.getMemberId()));
            return "member/myInfo";
        }
    }





    /**
     * 회원정보 수정
     * @param modifyData 바뀌지 않은 데이터와 변경된 데이터
     * @return 바뀐 데이터
     */
    @PostMapping("/modify-info")
    public String modifyInfo(MemberDto modifyData,RedirectAttributes redirectAttributes) {
        memberService.modifyMemberInfo(modifyData);

        redirectAttributes.addFlashAttribute("message", "변경에 성공하였습니다!");
        return "redirect:/member/my-info/info";
    }

    /**
     * 비밀번호 변경
     * @param password 바꿀 비밀번호
     * @param member 로그인된 회원 정보
     * @return 성공 true, 실패 false
     */
    @PostMapping("/modify-password")
    public String modifyPassword(@RequestParam("password")String password,@RequestParam("passwordCheck")String passwordCheck, @AuthenticationPrincipal Member member, RedirectAttributes redirectAttributes) {
        memberService.modifyPassword(member.getMemberId(), password,passwordCheck);

        redirectAttributes.addFlashAttribute("message", "변경에 성공하였습니다!");
        return "redirect:/member/my-info/info";

    }

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
