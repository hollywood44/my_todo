package com.share.my_todo.service;

import com.share.my_todo.DTO.notice.NoticeDto;
import com.share.my_todo.entity.common.FollowNoticeMessage;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.entity.notice.Notice;
import com.share.my_todo.repository.NoticeRepository;
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
    public List<NoticeDto> getNotReadNoticeList(Member member) {
        List<NoticeDto> noticeList = new ArrayList<>();
        List<Notice> entityList = noticeRepository.findAllByMemberAndReadAtIsNull(member);

        if (!entityList.isEmpty()) {
            noticeList = entityList.stream().map(e -> entityToDto(e)).collect(Collectors.toList());
        }

        return noticeList;
    }

    @Override
    public int readNotice(Member member) {
        List<Notice> list = noticeRepository.findAllByMemberAndReadAtIsNull(member);
        for (Notice notice : list) {
            notice.readNotice();
        }
        return noticeRepository.saveAll(list).size();
    }

    @Override
    public int checkNotice(Member member) {
        List<Notice> list = noticeRepository.findAllByMemberAndReadAtIsNull(member);

        return list.size();
    }

    @Override
    public Page<NoticeDto> getPrevNoticeList(Member member,int page) {
        Sort sort = Sort.by("noticeId").descending();
        Pageable pageable = PageRequest.of(page,30,sort); // page(번호)부터 10개씩 잘라서 보겠다
        Page<Notice> entityList = noticeRepository.findAllByMemberAndReadAtIsNotNull(member,pageable);
        if (!entityList.isEmpty()) {
            Page<NoticeDto> noticeList = entityList.map(e -> entityToDto(e));
            return noticeList;
        } else {
            Page<NoticeDto> noticeList = Page.empty();
            return noticeList;
        }
    }
}
