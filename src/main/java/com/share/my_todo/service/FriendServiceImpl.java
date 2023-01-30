package com.share.my_todo.service;

import com.share.my_todo.DTO.member.FriendDto;
import com.share.my_todo.DTO.member.FriendListDto;
import com.share.my_todo.entity.member.Friend;
import com.share.my_todo.entity.member.FriendList;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.repository.FriendListRepository;
import com.share.my_todo.repository.FriendRepository;
import com.share.my_todo.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{

    private final MemberRepository memberRepository;
    private final FriendListRepository listRepository;
    private final FriendRepository friendRepository;

    @Override
    public FriendListDto getFriendList(String memberId) {
        FriendList friendList = listRepository.findByMember(easyMakeMember(memberId)).get();
        FriendListDto friendListDto = entityToDtoForList(friendList);

        return friendListDto;
    }

    @Override
    @Transactional
    public Long followRequest(String myId, String followId) {
        FriendList friendList = listRepository.findByMember(Member.builder().memberId(myId).build()).get();
        Friend followFriend = Friend.builder().member(Member.builder().memberId(followId).build()).build();

        followFriend.statusToWaiting();
        friendList.addFriend(followFriend);

        FriendList followerFriendList = listRepository.findByMember(Member.builder().memberId(followId).build()).get();
        Friend follower = Friend.builder().member(Member.builder().memberId(myId).build()).build();

        follower.statusToRequest();
        followerFriendList.addFriend(follower);

        listRepository.save(followerFriendList);
        return listRepository.save(friendList).getFriendListId();
    }

    @Override
    @Transactional
    public Long followAccept(String myId, String followerId) {
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
    public Long followReject(String myId, String followerId) {
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
}