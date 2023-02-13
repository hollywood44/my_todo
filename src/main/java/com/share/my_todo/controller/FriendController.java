package com.share.my_todo.controller;

import com.share.my_todo.entity.member.Member;
import com.share.my_todo.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/friend")
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/list")
    public String friendListPage(@AuthenticationPrincipal Member member, Model model) {
        model.addAttribute("friendList", friendService.getFriendList(member.getMemberId()));

        return "friendList";
    }

    @PostMapping("/unfollow")
    public String unfollow(@RequestParam("unfollowMemberId") String unfollowMemberId, @AuthenticationPrincipal Member member) {
        friendService.unFollow(member.getMemberId(), unfollowMemberId);

        return "redirect:/friend/list";
    }

    @GetMapping("/requested-list")
    public String followRequestedListPage(@AuthenticationPrincipal Member member, Model model) {
        model.addAttribute("requestedList", friendService.requestedFriendList(member.getMemberId()));
        return "followRequested";
    }

    @GetMapping("/follow-accept")
    public String followAccept(@RequestParam("followerId") String followerId, @AuthenticationPrincipal Member member) {
        friendService.followAccept(member.getMemberId(), followerId);

        return "redirect:/friend/list";
    }

    @GetMapping("/follow-reject")
    public String followReject(@RequestParam("followerId") String followerId, @AuthenticationPrincipal Member member) {
        friendService.followReject(member.getMemberId(), followerId);

        return "redirect:/friend/list";
    }


    @PostMapping("/follow-request")
    public String followRequest(@RequestParam("followId") String followId, @AuthenticationPrincipal Member member, RedirectAttributes redirectAttributes) {
        Long status = friendService.followRequest(member.getMemberId(), followId);

        if (status == 0000L) {
            redirectAttributes.addFlashAttribute("msg", "이미 친구목록에 있는 친구입니다.");
            return "redirect:/friend/list";
        } else {
            return "redirect:/friend/list";
        }
    }
}
