package com.share.my_todo.controller;

import com.share.my_todo.DTO.chat.ChatDto;
import com.share.my_todo.DTO.chat.ChatRoomDto;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate template;

    @GetMapping("/chat/room-list")
    public ResponseEntity<List<ChatRoomDto>> chatRoomListPage() {
        List<ChatRoomDto> chatRoomList = chatService.getChatRoomList();

        return ResponseEntity.status(HttpStatus.OK).body(chatRoomList);
    }

    @GetMapping("/chat/history/{roomId}")
    public ResponseEntity<List<ChatDto>> getHistory(@PathVariable Long roomId) {
        List<ChatDto> history = chatService.getChatHistory(roomId);

        return ResponseEntity.status(HttpStatus.OK).body(history);
    }

    @GetMapping("/chat/{receiveMember}")
    public ResponseEntity<Long> createRoomOrOpenChatDetail(@AuthenticationPrincipal Member member,@PathVariable String receiveMember) {
        Long chatRoomId = chatService.createRoom(receiveMember);

        return ResponseEntity.status(HttpStatus.OK).body(chatRoomId);
    }

    @MessageMapping("/chat/sendMessage/{roomId}")
    public void message(@RequestBody ChatDto chatDto, @DestinationVariable Long roomId) {
        ChatDto receive = chatService.chatSave(chatDto);
        receive.setChatTime(receive.getChatTime().substring(0,receive.getChatTime().lastIndexOf(':')));
        template.convertAndSend("/sub/chat/receive/"+receive.getChatRoomId(),receive);
    }
}
