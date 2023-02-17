package com.share.my_todo.service.notice;

import com.share.my_todo.DTO.notice.NoticeDto;
import com.share.my_todo.entity.common.FollowNoticeMessage;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.entity.notice.Notice;
import org.springframework.data.domain.Page;

import java.util.List;

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


    /**
     * 회원 아이디, 진행률을 받아서 알림을 보낸다.
     * @param memberId 회원 아이디
     * @param percent 진행률
     * @return 보낸 알림 아이디
     */
    Long sendTodoProgressNotice(String memberId, int percent);

    /**
     * 팔로우 요청 알림을 보낸다.
     * @param sendMemberId 팔로우 한 회원 아이디
     * @param targetMemberId 팔로우 당한 회원 아이디
     * @return 보낸 알림 아이디
     */
    Long sendFollowRequestNotice(String sendMemberId, String targetMemberId);

    /**
     * 팔로우 수락 알림을 보낸다.
     * @param sendMemberId 수락한 회원 아이디
     * @param targetMemberId 수락받은 회원 아이디
     * @return 보낸 알림 아이디
     */
    Long sendFollowAcceptNotice(String sendMemberId, String targetMemberId);

    /**
     * 팔로우 거절 알림을 보낸다.
     * @param sendMemberId 팔로우 거절한 회원 아이디
     * @param targetMemberId 팔로우 거절당한 회원 아이디
     * @return 보낸 알림 아이디
     */
    Long sendFollowRejectNotice(String sendMemberId, String targetMemberId);


    /**
     * 완료 기한이 내일까지인 할일이 있다고 알림을 보낸다.
     * @param targetMemberId 받는 회원 아이디
     * @param todoContent 해야할 할일
     * @return 알림
     */
    Notice makeFinishDateTomorrowNotice(String targetMemberId, String todoContent);

    List<NoticeDto> getNotReadNoticeList(Member member);

    int readNotice(Member member);

    Page<NoticeDto> getPrevNoticeList(Member member,int page);

    int checkNotice(Member member);
}
