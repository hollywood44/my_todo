package com.share.my_todo.service.chat;

import com.share.my_todo.DTO.chat.ChatDto;
import com.share.my_todo.entity.chat.Chat;
import com.share.my_todo.entity.chat.ChatRoom;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.repository.chat.ChatRepository;
import com.share.my_todo.repository.chat.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService{

    private final ChatRoomRepository roomRepository;
    private final ChatRepository chatRepository;

    @Override
    public Long createRoom(String receiverId, String senderId) {
        Optional<Chat> chatRoom = chatRepository.findBySenderAndReceiver(Member.builder().memberId(receiverId).build(), Member.builder().memberId(senderId).build());
        if (!chatRoom.isPresent()) {
            ChatRoom createRoom = ChatRoom.builder().build();
            return roomRepository.save(createRoom).getChatroomId();
        } else {
            return chatRoom.get().getChatRoom().getChatroomId();
        }
    }

    @Override
    public ChatDto chatSave(ChatDto chat) {
        chat.setChatTime(LocalDateTime.now());
        chatRepository.save(chatDtoToEntity(chat));
        return chat;
    }
}
