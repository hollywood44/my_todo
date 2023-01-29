package com.share.my_todo.service;

import com.share.my_todo.DTO.member.FriendListDto;
import com.share.my_todo.entity.member.FriendList;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FriendServiceImplTest {

    @Autowired
    FriendService friendService;

    @Test
    void 친구목록가져오기() {
        FriendListDto friendList = friendService.getFriendList("member4");
        System.out.println(friendList.getFriendList());
    }

    @Test
    @Rollback(value = false)
    void 친구추가() {
        friendService.followRequest("member2", "member4");
    }


    }