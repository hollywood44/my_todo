package com.share.my_todo.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.share.my_todo.entity.member.FriendList;
import com.share.my_todo.entity.member.Member;

import javax.persistence.EntityManager;

import static com.share.my_todo.entity.member.QFriend.friend;


public class FriendRepositoryCustomImpl implements FriendRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public FriendRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Long findFriendForAccept(FriendList friendList, Member memberId) {
        Long result = queryFactory
                .select(friend.friendId)
                .from(friend)
                .where(
                        friend.member.eq(memberId)
                                .and(friend.friendList.eq(friendList))
                ).fetchOne();
        return result;
    }
}