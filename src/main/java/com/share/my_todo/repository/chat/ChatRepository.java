package com.share.my_todo.repository.chat;

import com.share.my_todo.entity.chat.Chat;
import com.share.my_todo.entity.chat.ChatRoom;
import com.share.my_todo.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat,Long> {

    Optional<Chat> findBySenderAndReceiver(Member sender, Member receiver);

    List<Chat> findAllByChatRoomOrderByChatTimeAsc(ChatRoom chatRoom);
}
