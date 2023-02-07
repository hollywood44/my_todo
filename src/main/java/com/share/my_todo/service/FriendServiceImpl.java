package com.share.my_todo.service;

import com.share.my_todo.DTO.member.FriendDto;
import com.share.my_todo.DTO.member.FriendListDto;
import com.share.my_todo.entity.member.Friend;
import com.share.my_todo.entity.member.FriendList;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.repository.FriendListRepository;
import com.share.my_todo.repository.FriendRepository;
import com.share.my_todo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{

    private final MemberRepository memberRepository;
    private final FriendListRepository listRepository;
    private final FriendRepository friendRepository;
    private final NoticeService noticeService;


    @Override
    public FriendListDto getFriendList(String memberId) {
        FriendList friendList = listRepository.findByMemberAndStatus(easyMakeMember(memberId),Friend.FollowStatus.Accept).get();
        FriendListDto friendListDto = entityToDtoForList(friendList);

        return friendListDto;
    }

    @Override
    @Transactional
    public Long followRequest(String myId, String followId) {
        // 팔로우 건 회원 친구목록 불러옴 그리고 여기 담길 Friend객체 생성
        FriendList friendList = listRepository.findByMember(Member.builder().memberId(myId).build()).get();
        Friend followFriend = Friend.builder().member(Member.builder().memberId(followId).build()).build();

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
        return listRepository.save(friendList).getFriendListId();
    }

    @Override
    @Transactional
    public Long followAccept(String myId, String followerId) {

        noticeService.sendFollowAcceptNotice(myId, followerId);

        FriendList friendList = listRepository.findByMember(easyMakeMember(myId)).get();
        FriendList followerFriendList = listRepository.findByMember(easyMakeMember(followerId)).get();

        Long friendId = friendRepository.findFriendForAccept(friendList,easyMakeMember(followerId));
        Long myFriendId = friendRepository.findFriendForAccept(followerFriendList,easyMakeMember(myId));

        for(Friend friend : friendList.getFriendList()){
            if (friend.getFriendId().equals(friendId)) {
                friend.statusToAccept();
                friendRepository.save(friend);
            }
        }

        for(Friend friend : followerFriendList.getFriendList()){
            if (friend.getFriendId().equals(myFriendId)) {
                friend.statusToAccept();
                friendRepository.save(friend);
            }
        }

        return friendId;
    }

    @Override
    public Long followReject(String myId, String followerId) {
        noticeService.sendFollowRejectNotice(myId, followerId);

        FriendList friendList = listRepository.findByMember(easyMakeMember(myId)).get();
        FriendList followerFriendList = listRepository.findByMember(easyMakeMember(followerId)).get();

        Long friendId = friendRepository.findFriendForAccept(friendList,easyMakeMember(followerId));
        Long myFriendId = friendRepository.findFriendForAccept(followerFriendList,easyMakeMember(myId));

        for(Friend friend : friendList.getFriendList()){
            if (friend.getFriendId().equals(friendId)) {
                friend.statusToReject();
                friendRepository.save(friend);
            }
        }

        for(Friend friend : followerFriendList.getFriendList()){
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
    @Transactional
    public List<Long> unFollow(String myId, String followId) {
        FriendList friendList = listRepository.findByMember(easyMakeMember(myId)).get();
        FriendList followerFriendList = listRepository.findByMember(easyMakeMember(followId)).get();

        Long friendId = friendRepository.findFriendForAccept(friendList,easyMakeMember(followId));
        Long myFriendId = friendRepository.findFriendForAccept(followerFriendList,easyMakeMember(myId));

        List<Long> deleteList = new ArrayList<>();
        deleteList.add(friendId);
        deleteList.add(myFriendId);

        friendRepository.deleteAllById(deleteList);

        return deleteList;
    }
}