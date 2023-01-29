package com.share.my_todo.repository;

import com.share.my_todo.entity.member.Friend;
import com.share.my_todo.entity.member.FriendList;
import com.share.my_todo.entity.member.Member;

import java.util.Optional;

public interface FriendRepositoryCustom {
    Long findFriendForAccept(FriendList friendList, Member memberId);
}
