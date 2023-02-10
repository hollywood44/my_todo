package com.share.my_todo.controller;

import com.share.my_todo.entity.member.Member;
import com.share.my_todo.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
