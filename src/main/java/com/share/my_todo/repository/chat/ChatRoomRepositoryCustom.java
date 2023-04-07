package com.share.my_todo.repository.chat;

import com.share.my_todo.entity.chat.ChatRoom;
import com.share.my_todo.entity.member.Member;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepositoryCustom {

    public Optional<ChatRoom> findChatRoom(Member member1, Member member2);
    public boolean checkChatAuthority(String memberId,Long chatRoomId);

    public List<ChatRoom> findChatRoomByMemberId(String memberId);
}
