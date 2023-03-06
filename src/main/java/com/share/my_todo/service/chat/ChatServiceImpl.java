package com.share.my_todo.service.chat;

import com.share.my_todo.DTO.chat.ChatDto;
import com.share.my_todo.DTO.chat.ChatRoomDto;
import com.share.my_todo.entity.chat.Chat;
import com.share.my_todo.entity.chat.ChatRoom;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.repository.chat.ChatRepository;
import com.share.my_todo.repository.chat.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatServiceImpl implements ChatService{

    private final ChatRoomRepository roomRepository;
    private final ChatRepository chatRepository;

    @Override
    public Long createRoom(String receiverId, String senderId) {
        Optional<ChatRoom> chatRoom = roomRepository.findChatRoom(Member.easyMakeMember(receiverId),Member.easyMakeMember(senderId));
        if (!chatRoom.isPresent()) {
            ChatRoom createRoom = ChatRoom.builder().memberOneId(Member.builder().memberId(receiverId).build()).memberTwoId(Member.builder().memberId(senderId).build()).build();
            return roomRepository.save(createRoom).getChatroomId();
        } else {
            return chatRoom.get().getChatroomId();
        }
    }

    @Override
    public ChatDto chatSave(ChatDto chat) {
        chatRepository.save(chatDtoToEntity(chat));
        return chat;
    }

    @Override
    public List<ChatRoomDto> getChatRoomList(Member member) {
        List<ChatRoom> entityRoomList = roomRepository.findByMemberOneIdOrMemberTwoId(member, member);
        if (!entityRoomList.isEmpty()) {
            List<ChatRoomDto> roomList = entityRoomList.stream().map(e -> roomEntityToDto(e, 1)).collect(Collectors.toList());
            return roomList;
        } else {
            List<ChatRoomDto> list = new ArrayList<>();
            return list;
        }
    }

    @Override
    public List<ChatDto> getChatHistory(Long chatRoomId) {
        List<Chat> entityHistory = chatRepository.findAllByChatRoomOrderByChatTimeAsc(ChatRoom.builder().chatroomId(chatRoomId).build());
        if (!entityHistory.isEmpty()) {
            List<ChatDto> chatHistory = entityHistory.stream().map(e -> chatEntityToDto(e)).collect(Collectors.toList());
            return chatHistory;
        } else {
            List<ChatDto> emptyList = new ArrayList<>();
            return emptyList;
        }
    }
}
