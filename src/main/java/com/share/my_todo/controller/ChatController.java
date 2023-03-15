package com.share.my_todo.controller;

import com.share.my_todo.DTO.chat.ChatDto;
import com.share.my_todo.DTO.chat.ChatRoomDto;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.service.chat.ChatService;
import lombok.RequiredArgsConstructor;
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
    public String chatRoomListPage(@AuthenticationPrincipal Member member, Model model) {
        List<ChatRoomDto> chatRoomList = chatService.getChatRoomList(member);
        model.addAttribute("chatRoomList", chatRoomList);

        return "chat/chatList";
    }

    @GetMapping("/chat/{roomId}/{chatMember}")
    public String chatRoomDetail(@AuthenticationPrincipal Member member, @PathVariable Long roomId,@PathVariable String chatMember,Model model) {
        List<ChatDto> history = chatService.getChatHistory(roomId);
        model.addAttribute("history", history);

        return "chat/chatDetail";
    }

    @GetMapping("/chat/{receiveMember}")
    public String createRoomOrOpenChatDetail(@AuthenticationPrincipal Member member,@PathVariable String receiveMember) {
        Long chatRoomId = chatService.createRoom(receiveMember,member.getMemberId());
        return "redirect:/chat/" + chatRoomId + "/" + receiveMember;
    }

    @MessageMapping("/sendMessage/{roomId}/{senderId}")
    public void message(@RequestBody ChatDto chatDto, @DestinationVariable Long roomId,@DestinationVariable String senderId) {
        ChatDto receive = chatService.chatSave(chatDto);
        template.convertAndSend("/sub/chat/receive/"+receive.getChatRoomId(),receive);
    }
}
