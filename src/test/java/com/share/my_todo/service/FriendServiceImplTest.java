package com.share.my_todo.service;

import com.share.my_todo.DTO.member.FriendListDto;
import com.share.my_todo.entity.member.Friend;
import com.share.my_todo.entity.member.FriendList;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.repository.FriendListRepository;
import com.share.my_todo.repository.FriendRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        FriendListDto friendList = friendService.getFriendList("member6");
        System.out.println(friendList.getFriendList());
    }

    @Test
    @Rollback(value = false)
    void 팔로우요청() {
        friendService.followRequest("member7", "member8");
    }

    @Test
    @Rollback(value = false)
    void 팔로우수락() {
        friendService.followAccept("member2", "member7");
    }

    @Test
    @Rollback(value = false)
    void 팔로우거부() {
        friendService.followReject("member8", "member7");
    }

    @Test
    void 테스트() {
        FriendList friendList = listRepository.findByMember(Member.builder().memberId("member7").build()).get();
//        List<Friend> friendList = friendRepository.findAll();
        for (Friend friend : friendList.getFriendList()) {
            System.out.println("list Id : " + friend.getFriendList().getFriendListId());
        }
//                    System.out.println("list Id : " + friendList.getFriendListId());

    }

    }