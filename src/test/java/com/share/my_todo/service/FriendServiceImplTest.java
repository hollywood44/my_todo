package com.share.my_todo.service;

import com.share.my_todo.DTO.member.FriendListDto;
import com.share.my_todo.repository.friend.FriendListRepository;
import com.share.my_todo.repository.friend.FriendRepository;
import com.share.my_todo.service.friend.FriendService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
class FriendServiceImplTest {

    @Autowired
    FriendService friendService;
    @Autowired
    FriendRepository friendRepository;
    @Autowired
    FriendListRepository listRepository;

    @Test
    void 친구목록가져오기() {
        FriendListDto friendList = friendService.getFriendList("member7");
        System.out.println("************************");
        System.out.println(friendList.getFriendList());
        System.out.println("************************");
    }

    @Test
    @Rollback(value = false)
    void 팔로우요청() {
        friendService.followRequest("member7", "member8");
        friendService.followRequest("member7", "member6");
        friendService.followRequest("member7", "member2");

    }

    @Test
    @Rollback(value = false)
    void 팔로우수락() {
        friendService.followAccept("member2", "member7");
        friendService.followAccept("member6", "member7");
    }

    @Test
    @Rollback(value = false)
    void 팔로우거부() {
        friendService.followReject("member8", "member7");
        friendService.followReject("member6", "member7");
        friendService.followReject("member2", "member7");
    }

    @Test
    @Rollback(value = false)
    void 팔로우취소() {
        friendService.unFollow("member7","member2");
    }




}