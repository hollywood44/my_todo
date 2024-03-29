package com.share.my_todo.service.notice;

import com.share.my_todo.DTO.notice.NoticeDto;
import com.share.my_todo.util.SecurityUtil;
import com.share.my_todo.entity.common.FollowNoticeMessage;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.entity.notice.Notice;
import com.share.my_todo.repository.notice.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeServiceImpl implements NoticeService{

    private final NoticeRepository noticeRepository;

    @Override
    public Long sendTodoProgressNotice(String memberId, int percent) {
        return null;
    }

    @Override
    public Long sendFollowRequestNotice(String sendMemberId, String targetMemberId) {
        Notice notice = makeFollowStatusNotice(sendMemberId,targetMemberId,FollowNoticeMessage.FollowRequest);

        return noticeRepository.save(notice).getNoticeId();
    }

    @Override
    public Long sendFollowAcceptNotice(String sendMemberId, String targetMemberId) {
        Notice notice = makeFollowStatusNotice(sendMemberId,targetMemberId,FollowNoticeMessage.FollowAccept);

        return noticeRepository.save(notice).getNoticeId();
    }

    @Override
    public Long sendFollowRejectNotice(String sendMemberId, String targetMemberId) {
        Notice notice = makeFollowStatusNotice(sendMemberId,targetMemberId,FollowNoticeMessage.FollowReject);

        return noticeRepository.save(notice).getNoticeId();
    }

    @Override
    public Notice makeFinishDateTomorrowNotice(String targetMemberId, String todoContent) {
        Notice notice = Notice.builder()
                .notice(todoContent + " 의 마감일이 내일입니다.")
                .member(Member.builder().memberId(targetMemberId).build())
                .build();
        return notice;
    }

    @Override
    public List<NoticeDto> getNotReadNoticeList() {
        List<NoticeDto> noticeList = new ArrayList<>();
        List<Notice> entityList = noticeRepository.findAllByMemberAndReadAtIsNull(Member.easyMakeMember(SecurityUtil.getCurrentMemberId()));

        if (!entityList.isEmpty()) {
            noticeList = entityList.stream().map(e -> entityToDto(e)).collect(Collectors.toList());
        }

        return noticeList;
    }

    @Override
    public void readNotice() {
        List<Notice> list = noticeRepository.findAllByMemberAndReadAtIsNull(Member.easyMakeMember(SecurityUtil.getCurrentMemberId()));

        if (!list.isEmpty()) {
            for (Notice notice : list) {
                notice.readNotice();
            }
            noticeRepository.saveAll(list);
        }
    }

    @Override
    public Integer checkNotice() {
        List<Notice> list = noticeRepository.findAllByMemberAndReadAtIsNull(Member.easyMakeMember(SecurityUtil.getCurrentMemberId()));

        return list.size();
    }

    @Override
    public Page<NoticeDto> getPrevNoticeList(int page) {
        Sort sort = Sort.by("noticeId").descending();
        Pageable pageable = PageRequest.of(page,30,sort); // page(번호)부터 10개씩 잘라서 보겠다
        Page<Notice> entityList = noticeRepository.findAllByMemberAndReadAtIsNotNull(Member.easyMakeMember(SecurityUtil.getCurrentMemberId()),pageable);
        if (!entityList.isEmpty()) {
            Page<NoticeDto> noticeList = entityList.map(e -> entityToDto(e));
            return noticeList;
        } else {
            Page<NoticeDto> noticeList = Page.empty();
            return noticeList;
        }
    }
}
