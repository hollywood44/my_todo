package com.share.my_todo.repository.chat;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.share.my_todo.entity.chat.ChatRoom;
import static com.share.my_todo.entity.chat.QChatRoom.chatRoom;
import com.share.my_todo.entity.member.Member;

import javax.persistence.EntityManager;
import java.util.Optional;

public class ChatRoomRepositoryCustomImpl implements ChatRoomRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public ChatRoomRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Optional<ChatRoom> findChatRoom(Member member1, Member member2) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(chatRoom.memberOneId.eq(member1));
        builder.and(chatRoom.memberTwoId.eq(member2));

        BooleanBuilder builder2 = new BooleanBuilder();
        builder2.and(chatRoom.memberOneId.eq(member2));
        builder2.and(chatRoom.memberTwoId.eq(member1));

        Optional<ChatRoom> result = queryFactory
                .selectFrom(chatRoom)
                .where(builder.or(builder2))
                .stream().findAny();
        return result;
    }

    @Override
    public boolean checkChatAuthority(String memberId,Long chatRoomId) {
        Integer result = queryFactory
                .selectOne()
                .from(chatRoom)
                .where(chatRoom.chatroomId.eq(chatRoomId)
                        .and(chatRoom.memberTwoId.memberId.eq(memberId).or(chatRoom.memberOneId.memberId.eq(memberId))))
                .fetchFirst();

        return result != null;
    }
}
