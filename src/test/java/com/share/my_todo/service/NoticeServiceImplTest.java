package com.share.my_todo.service;

import com.share.my_todo.DTO.notice.NoticeDto;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.repository.notice.NoticeRepository;
import com.share.my_todo.service.notice.NoticeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class NoticeServiceImplTest {

    @Autowired
    NoticeService noticeService;
    @Autowired
    NoticeRepository noticeRepository;

    @Test
    @Rollback(value = false)
    void 팔로우요청테스트() {
        noticeService.sendFollowRequestNotice("member2", "member6");
    }

    @Test
    void 알림조회테스트() {
        List<NoticeDto> list = noticeService.getNotReadNoticeList();
        if (!list.isEmpty()) {
            for (NoticeDto item : list) {
                System.out.println(item.getNotice());
            }
        }
    }
}