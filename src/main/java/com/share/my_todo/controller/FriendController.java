package com.share.my_todo.controller;

import com.share.my_todo.DTO.member.FriendDto;
import com.share.my_todo.DTO.member.FriendListDto;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.service.friend.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/friends")
public class FriendController {

    private final FriendService friendService;

    @GetMapping("/list")
    public ResponseEntity<FriendListDto> getFriendList() {
        FriendListDto friendList = friendService.getFriendList();

        return ResponseEntity.status(HttpStatus.OK).body(friendList);
    }

    @GetMapping("/requested-list")
    public ResponseEntity<List<FriendDto>> followRequestedListPage() {
        List<FriendDto> requestedList = friendService.requestedFriendList();

        return ResponseEntity.status(HttpStatus.OK).body(requestedList);
    }

    @GetMapping("/request-list")
    public ResponseEntity<List<FriendDto>> followRequestList() {
        List<FriendDto> requestList = friendService.requestFriendList();

        return ResponseEntity.status(HttpStatus.OK).body(requestList);
    }

    @PostMapping("/follow-accept")
    public ResponseEntity<?> followAccept(@RequestBody Map<String,String> acceptMap) {
        friendService.followAccept(acceptMap.get("followerId"));

        return ResponseEntity.status(HttpStatus.OK).body("follow accept success");
    }

    @PostMapping("/follow-reject")
    public ResponseEntity<?> followReject(@RequestBody Map<String,String> rejectMap) {
        friendService.followReject(rejectMap.get("followerId"));

        return ResponseEntity.status(HttpStatus.OK).body("follow Reject success");
    }


    @PostMapping("/follow-request")
    public ResponseEntity<?> followRequest(@RequestBody Map<String,String> requestMap) {
        friendService.followRequest(requestMap.get("followId"));

        return ResponseEntity.status(HttpStatus.OK).body("follow request success");
    }


    @PostMapping({"/unfollow","/request-cancel"})
    public ResponseEntity<?> unfollow(@RequestBody Map<String,String> unfollowMap) {
        friendService.unFollow(unfollowMap.get("followId"));

        return ResponseEntity.status(HttpStatus.OK).body("unfollow(cancel) success");
    }

}
