package com.share.my_todo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class NoticeServiceImplTest {

    @Autowired
    NoticeService noticeService;

    @Test
    @Rollback(value = false)
    void 팔로우요청테스트() {
        noticeService.sendFollowRequestNotice("member2", "member6");
    }
}