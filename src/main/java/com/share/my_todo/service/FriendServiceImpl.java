package com.share.my_todo.service;

import com.share.my_todo.DTO.member.FriendListDto;
import com.share.my_todo.entity.member.Friend;
import com.share.my_todo.entity.member.FriendList;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.repository.FriendListRepository;
import com.share.my_todo.repository.FriendRepository;
import com.share.my_todo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendServiceImpl implements FriendService{

    private final MemberRepository memberRepository;
    private final FriendListRepository listRepository;
    private final FriendRepository friendRepository;

    @Override
    public FriendListDto getFriendList(String memberId) {
        FriendList friendList = listRepository.findByMember(Member.builder().memberId(memberId).build()).get();
        FriendListDto friendListDto = entityToDtoForList(friendList);

        return friendListDto;
    }

    @Override
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
    public Long followAccept(String myId, String followerId) {
        return null;
    }
}
