package com.share.my_todo.controller;

import com.share.my_todo.DTO.member.MemberDto;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.exception.ErrorCode;
import com.share.my_todo.exception.ErrorResponse;
import com.share.my_todo.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLDecoder;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {


    private final MemberService memberService;

    @GetMapping("/main")
    public String introPage() {
        return "start";
    }

    @GetMapping("/signIn")
    public String loginPage(@RequestParam(value = "error",required = false) String isError,@RequestParam(value = "message",required = false)String error,
                            RedirectAttributes redirectAttributes) {
        if (isError != null) {
            ErrorResponse errorResponse;
            switch (error) {
                case "LOGIN_ERROR_ID_PASSWORD" : errorResponse = new ErrorResponse(ErrorCode.LOGIN_ERROR_ID_PASSWORD);break;
                case "LOGIN_ERROR_ID_NOT_PRESENT" : errorResponse = new ErrorResponse(ErrorCode.LOGIN_ERROR_ID_NOT_PRESENT);break;
                default: errorResponse = new ErrorResponse(ErrorCode.LOGIN_ERROR_UNDEFINED);
            }
            redirectAttributes.addFlashAttribute("error", errorResponse);
            return "redirect:/member/signIn";
        }

        return "member/login";
    }

    @GetMapping("/signUp")
    public String signUpPage() {
        return "member/signUp";
    }

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
     * ????????????
     * @param signUpData ??????????????? ????????? ?????????
     * @return ???????????? ????????? ?????????
     */
    @PostMapping("/signUp")
    public String signUp(MemberDto signUpData) {
        memberService.signUp(signUpData);
        return "redirect:/member/signIn";
    }

    /**
     * ????????? ????????????
     * @param memberId ???????????? ??? ?????????
     * @return true / false
     */
    @GetMapping("/signup/check")
    public boolean idCheck(@RequestParam("memberId")String memberId) {
        return memberService.idCheck(memberId);
    }

    /**
     * ???????????? ??????
     * @param modifyData ????????? ?????? ???????????? ????????? ?????????
     * @return ?????? ?????????
     */
    @PostMapping("/modify-info")
    public String modifyInfo(MemberDto modifyData,RedirectAttributes redirectAttributes) {
        memberService.modifyMemberInfo(modifyData);

        redirectAttributes.addFlashAttribute("message", "????????? ?????????????????????!");
        return "redirect:/member/my-info/info";
    }

    /**
     * ???????????? ??????
     * @param password ?????? ????????????
     * @param member ???????????? ?????? ??????
     * @return ?????? true, ?????? false
     */
    @PostMapping("/modify-password")
    public String modifyPassword(@RequestParam("password")String password,@RequestParam("passwordCheck")String passwordCheck, @AuthenticationPrincipal Member member, RedirectAttributes redirectAttributes) {
        memberService.modifyPassword(member.getMemberId(), password,passwordCheck);

        redirectAttributes.addFlashAttribute("message", "????????? ?????????????????????!");
        return "redirect:/member/my-info/info";

    }

    /**
     * ?????? ??????
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
