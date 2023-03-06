package com.share.my_todo.repository.friend;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.share.my_todo.entity.member.*;

import javax.persistence.EntityManager;
import java.util.Optional;


public class FriendListRepositoryCustomImpl implements FriendListRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private static QFriend friend = QFriend.friend;
    private static QFriendList friendList = QFriendList.friendList1;

    public FriendListRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }


    @Override
    public Optional<FriendList> findByMemberAndStatus(Member member, Friend.FollowStatus status) {
        Optional<FriendList> result = queryFactory
                .selectFrom(friendList)
                .join(friendList.friendList,friend).fetchJoin()
                .where(friendList.member.eq(member),friend.followStatus.eq(status))
                .distinct()
                .stream().findAny();
        return result;
    }
}
