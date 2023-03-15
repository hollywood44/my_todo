package com.share.my_todo.service.friend;

import com.share.my_todo.DTO.member.FriendDto;
import com.share.my_todo.DTO.member.FriendListDto;
import com.share.my_todo.util.SecurityUtil;
import com.share.my_todo.entity.member.Friend;
import com.share.my_todo.entity.member.FriendList;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.exception.ErrorCode;
import com.share.my_todo.exception.exceptionClass.CommonException;
import com.share.my_todo.repository.friend.FriendListRepository;
import com.share.my_todo.repository.friend.FriendRepository;
import com.share.my_todo.repository.member.MemberRepository;
import com.share.my_todo.service.notice.NoticeService;
import com.share.my_todo.service.todo.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService {

    private final MemberRepository memberRepository;
    private final FriendListRepository listRepository;
    private final FriendRepository friendRepository;
    private final NoticeService noticeService;
    private final TodoService todoService;


    @Override
    @Transactional
    public FriendListDto getFriendList() {
        Optional<FriendList> friendList = listRepository.findByMemberAndStatus(easyMakeMember(SecurityUtil.getCurrentMemberId()), Friend.FollowStatus.Accept);

        if (friendList.isPresent()) {
            FriendListDto friendListDto = entityToDtoForList(friendList.get());
            for (FriendDto friend : friendListDto.getFriendList()) {
                int archievement = todoService.getAchievementRate(todoService.getWeekTodoList(friend.getMemberId(), 1));
                friend.setLastWeekRate(archievement);

                archievement = todoService.getAchievementRate(todoService.getWeekTodoList(friend.getMemberId(), 0));
                friend.setThisWeekRate(archievement);
            }
            return friendListDto;
        }

        return new FriendListDto();
    }

    @Override
    @Transactional
    public void followRequest(String followId){
        String myId = SecurityUtil.getCurrentMemberId();

        // 팔로우 건 회원 친구목록 불러옴 그리고 여기 담길 Friend객체 생성
        FriendList friendList = listRepository.findByMember(Member.builder().memberId(myId).build()).get();
        Friend followFriend = Friend.builder().member(Member.builder().memberId(followId).build()).build();

        for (Friend friend : friendList.getFriendList()) {
            if (friend.getFollowStatus().equals(Friend.FollowStatus.Reject)) {
                throw new CommonException(ErrorCode.ALREADY_EXIST_FRIEND);
            }
            if (friend.getMember().getMemberId().equals(followId)) {
                throw new CommonException(ErrorCode.CAN_FOLLOW_AFTER_TWO_DAYS);
            }
        }
        // 팔로우 상태 세팅
        followFriend.statusToWaiting();
        friendList.addFriend(followFriend);

        // 팔로우 걸린 회원 친구목록 불러옴 그리고 여기 담길 Friend객체 생성
        FriendList followerFriendList = listRepository.findByMember(Member.builder().memberId(followId).build()).get();
        Friend follower = Friend.builder().member(Member.builder().memberId(myId).build()).build();

        // 팔로우 상태 세팅
        follower.statusToRequest();
        followerFriendList.addFriend(follower);

        // 알림 보냄
        noticeService.sendFollowRequestNotice(myId, followId);

        // 친구목록 저장
        listRepository.save(followerFriendList);
        listRepository.save(friendList);
    }

    @Override
    @Transactional
    public void followAccept(String followerId) {
        String myId = SecurityUtil.getCurrentMemberId();

        noticeService.sendFollowAcceptNotice(myId, followerId);

        FriendList friendList = listRepository.findByMember(easyMakeMember(myId)).get();
        FriendList followerFriendList = listRepository.findByMember(easyMakeMember(followerId)).get();

        Long friendId = friendRepository.findFriendForAccept(friendList, easyMakeMember(followerId));
        Long myFriendId = friendRepository.findFriendForAccept(followerFriendList, easyMakeMember(myId));

        for (Friend friend : friendList.getFriendList()) {
            if (friend.getFriendId().equals(friendId)) {
                friend.statusToAccept();
                friendRepository.save(friend);
            }
        }

        for (Friend friend : followerFriendList.getFriendList()) {
            if (friend.getFriendId().equals(myFriendId)) {
                friend.statusToAccept();
                friendRepository.save(friend);
            }
        }
    }

    @Override
    @Transactional
    public void followReject(String followerId) {
        String myId = SecurityUtil.getCurrentMemberId();

        noticeService.sendFollowRejectNotice(myId, followerId);

        FriendList friendList = listRepository.findByMember(easyMakeMember(myId)).get();
        FriendList followerFriendList = listRepository.findByMember(easyMakeMember(followerId)).get();

        Long friendId = friendRepository.findFriendForAccept(friendList, easyMakeMember(followerId));
        Long myFriendId = friendRepository.findFriendForAccept(followerFriendList, easyMakeMember(myId));

        for (Friend friend : friendList.getFriendList()) {
            if (friend.getFriendId().equals(friendId)) {
                friend.statusToReject();
                friendRepository.save(friend);
            }
        }

        for (Friend friend : followerFriendList.getFriendList()) {
            if (friend.getFriendId().equals(myFriendId)) {
                friend.statusToReject();
                friendRepository.save(friend);
            }
        }
    }

    @Override
    public List<FriendDto> requestedFriendList() {
        Optional<FriendList> friendList = listRepository.findByMemberAndStatus(easyMakeMember(SecurityUtil.getCurrentMemberId()), Friend.FollowStatus.Requested);
        List<FriendDto> requestedList = new ArrayList<>();

        if (friendList.isPresent()) {
            for (Friend friend : friendList.get().getFriendList()) {
                requestedList.add(entityToDto(friend));
            }
        }
        return requestedList;
    }

    @Override
    public List<FriendDto> requestFriendList() {
        Optional<FriendList> friendList = listRepository.findByMemberAndStatus(easyMakeMember(SecurityUtil.getCurrentMemberId()), Friend.FollowStatus.Waiting);
        List<FriendDto> requestList = new ArrayList<>();

        if (friendList.isPresent()) {
            for (Friend friend : friendList.get().getFriendList()) {
                requestList.add(entityToDto(friend));
            }
        }

        return requestList;
    }

    @Override
    @Transactional
    public void unFollow(String followId) {
        String myId = SecurityUtil.getCurrentMemberId();

        FriendList friendList = listRepository.findByMember(easyMakeMember(myId)).get();
        FriendList followerFriendList = listRepository.findByMember(easyMakeMember(followId)).get();

        Long friendId = friendRepository.findFriendForAccept(friendList, easyMakeMember(followId));
        Long myFriendId = friendRepository.findFriendForAccept(followerFriendList, easyMakeMember(myId));

        List<Long> deleteList = new ArrayList<>();
        deleteList.add(friendId);
        deleteList.add(myFriendId);

        friendRepository.deleteAllById(deleteList);
    }
}