package com.share.my_todo.service;

import com.share.my_todo.entity.common.FollowNoticeMessage;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.entity.notice.Notice;
import com.share.my_todo.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository noticeRepository;

    @Override
    public Long sendTodoProgressNotice(String memberId, int percent) {
        return null;
    }

    @Override
    public Long sendFollowRequestNotice(String followerId, String targetMemberId) {
        Notice notice = makeFollowStatusNotice(followerId,targetMemberId,FollowNoticeMessage.FollowRequest);

        return noticeRepository.save(notice).getNoticeId();
    }
}
