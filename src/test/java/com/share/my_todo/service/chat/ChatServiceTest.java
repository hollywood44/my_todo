package com.share.my_todo.service.chat;

import com.share.my_todo.DTO.chat.ChatDto;
import com.share.my_todo.DTO.chat.ChatRoomDto;
import com.share.my_todo.entity.member.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ChatServiceTest {

    @Autowired
    ChatService chatService;

    @Test
    void createRoom() {
        Long roomId = chatService.createRoom("member12", "member7");
        System.out.println(roomId);
    }

    @Test
    void chatSave() {
//        ChatDto message = ChatDto.builder().chatRoomId(3L).message("안녕하세요!").senderId("member10").receiverId("member7").build();
        ChatDto message = ChatDto.builder().chatRoomId(3L).message("네 안녕하세요!").senderId("member7").receiverId("member10").build();
        System.out.println(chatService.chatSave(message));
    }

    @Test
    void getRoomList() {
        List<ChatRoomDto> list = chatService.getChatRoomList(Member.builder().memberId("member7").build());
        list.stream().forEach(i -> System.out.println(i));
    }

    @Test
    void getChatHistory() {
        List<ChatDto> history = chatService.getChatHistory(3L);
        history.stream().forEach(i -> System.out.println(i));
    }

    @Test
    void 채팅방하나만생성하는지(){
        Long roomId = chatService.createRoom("member10", "member7");
        System.out.println(roomId);
        Long roomId2 = chatService.createRoom("member7", "member10");
        System.out.println(roomId2);
    }
}