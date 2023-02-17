package com.share.my_todo.service.chat;

import com.share.my_todo.DTO.chat.ChatDto;
import com.share.my_todo.entity.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ChatServiceTest {

    @Autowired
    ChatService chatService;

    @Test
    void createRoom() {
        Long roomId = chatService.createRoom("member7", "member10");
        System.out.println(roomId);
    }

    @Test
    void chatSave() {
        ChatDto message = ChatDto.builder().chatRoomId(1L).message("안녕하세요!").senderId("member10").receiverId("member7").build();
        System.out.println(chatService.chatSave(message));
    }
}