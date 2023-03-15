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
    }

    @Test
    @Rollback(value = false)
    void 팔로우요청() {
    }

    @Test
    @Rollback(value = false)
    void 팔로우수락() {
    }

    @Test
    @Rollback(value = false)
    void 팔로우거부() {

    }

    @Test
    @Rollback(value = false)
    void 팔로우취소() {
    }




}