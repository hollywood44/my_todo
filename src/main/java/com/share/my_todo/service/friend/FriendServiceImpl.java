package com.share.my_todo.service.friend;

import com.share.my_todo.DTO.member.FriendDto;
import com.share.my_todo.DTO.member.FriendListDto;
import com.share.my_todo.entity.member.Friend;
import com.share.my_todo.entity.member.FriendList;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.exception.exceptionClass.FollowRequestException;
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
    public FriendListDto getFriendList(String memberId) {
        Optional<FriendList> friendList = listRepository.findByMemberAndStatus(easyMakeMember(memberId), Friend.FollowStatus.Accept);
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
    public Long followRequest(String myId, String followId) throws FollowRequestException {
        // ????????? ??? ?????? ???????????? ????????? ????????? ?????? ?????? Friend?????? ??????
        FriendList friendList = listRepository.findByMember(Member.builder().memberId(myId).build()).get();
        Friend followFriend = Friend.builder().member(Member.builder().memberId(followId).build()).build();

        for (Friend friend : friendList.getFriendList()) {
            if (friend.getFollowStatus().equals(Friend.FollowStatus.Reject)) {
                return 0001L;
            }
            if (friend.getMember().getMemberId().equals(followId)) {
                return 0000L;
            }
        }
        // ????????? ?????? ??????
        followFriend.statusToWaiting();
        friendList.addFriend(followFriend);

        // ????????? ?????? ?????? ???????????? ????????? ????????? ?????? ?????? Friend?????? ??????
        FriendList followerFriendList = listRepository.findByMember(Member.builder().memberId(followId).build()).get();
        Friend follower = Friend.builder().member(Member.builder().memberId(myId).build()).build();

        // ????????? ?????? ??????
        follower.statusToRequest();
        followerFriendList.addFriend(follower);

        // ?????? ??????
        noticeService.sendFollowRequestNotice(myId, followId);

        // ???????????? ??????
        listRepository.save(followerFriendList);
        return listRepository.save(friendList).getFriendListId();
    }

    @Override
    @Transactional
    public Long followAccept(String myId, String followerId) {

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

        return friendId;
    }

    @Override
    @Transactional
    public Long followReject(String myId, String followerId) {
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

        return friendId;
    }

    @Override
    public List<FriendDto> requestedFriendList(String myId) {
        FriendList friendList = listRepository.findByMember(Member.builder().memberId(myId).build()).get();
        List<FriendDto> requestedList = new ArrayList<>();

        for (Friend friend : friendList.getFriendList()) {
            if (friend.getFollowStatus().equals(Friend.FollowStatus.Requested)) {
                requestedList.add(entityToDto(friend));
            }
        }
        return requestedList;
    }

    @Override
    public List<FriendDto> requestFriendList(String myId) {
        Optional<FriendList> friendList = listRepository.findByMemberAndStatus(easyMakeMember(myId), Friend.FollowStatus.Waiting);
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
    public List<Long> unFollow(String myId, String followId) {
        FriendList friendList = listRepository.findByMember(easyMakeMember(myId)).get();
        FriendList followerFriendList = listRepository.findByMember(easyMakeMember(followId)).get();

        Long friendId = friendRepository.findFriendForAccept(friendList, easyMakeMember(followId));
        Long myFriendId = friendRepository.findFriendForAccept(followerFriendList, easyMakeMember(myId));

        List<Long> deleteList = new ArrayList<>();
        deleteList.add(friendId);
        deleteList.add(myFriendId);

        friendRepository.deleteAllById(deleteList);

        return deleteList;
    }
}