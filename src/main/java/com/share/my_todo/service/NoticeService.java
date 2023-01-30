package com.share.my_todo.service;

import com.share.my_todo.DTO.notice.NoticeDto;
import com.share.my_todo.entity.common.FollowNoticeMessage;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.entity.notice.Notice;

public interface NoticeService {

    default NoticeDto entityToDto(Notice entity) {
        NoticeDto dto = NoticeDto.builder()
                .noticeId(entity.getNoticeId())
                .notice(entity.getNotice())
                .memberId(entity.getMember().getMemberId())
                .build();
        return dto;
    }

    default Notice makeFollowStatusNotice(String followerId, String targetMemberId, FollowNoticeMessage status) {
        Notice notice = Notice.builder()
                .member(Member.builder().memberId(targetMemberId).build())
                .notice(status.getMessage(followerId))
                .build();
        return notice;
    }

    Long sendTodoProgressNotice(String memberId, int percent);

    Long sendFollowRequestNotice(String followerId, String targetMemberId);
}
