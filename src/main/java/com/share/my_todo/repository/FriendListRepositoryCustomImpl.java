package com.share.my_todo.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.share.my_todo.entity.member.Friend;
import com.share.my_todo.entity.member.FriendList;
import com.share.my_todo.entity.member.Member;
import static com.share.my_todo.entity.member.QFriendList.friendList1;
import static com.share.my_todo.entity.member.QFriend.friend;

import javax.persistence.EntityManager;
import java.util.Optional;


public class FriendListRepositoryCustomImpl implements FriendListRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public FriendListRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public Optional<FriendList> findByMemberAndStatus(Member member, Friend.FollowStatus status) {
        Optional<FriendList> result = queryFactory
                .select(friendList1)
                .from(friendList1)
                .where(
                        friendList1.member.eq(member)
                        .and(friend.followStatus.eq(status))
                ).stream().findAny();
        return result;
    }
}
