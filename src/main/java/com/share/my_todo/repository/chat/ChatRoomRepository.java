package com.share.my_todo.repository.chat;

import com.share.my_todo.entity.chat.ChatRoom;
import com.share.my_todo.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom,Long>,ChatRoomRepositoryCustom {

    Optional<ChatRoom> findByMemberOneIdAndMemberTwoId(Member memberOne, Member memberTwo);

    List<ChatRoom> findByMemberOneIdOrMemberTwoId(Member memberOne, Member memberTwo);
}
