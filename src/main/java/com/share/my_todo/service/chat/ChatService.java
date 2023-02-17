package com.share.my_todo.service.chat;

import com.share.my_todo.DTO.chat.ChatDto;
import com.share.my_todo.DTO.chat.ChatRoomDto;
import com.share.my_todo.entity.chat.Chat;
import com.share.my_todo.entity.chat.ChatRoom;
import com.share.my_todo.entity.member.Member;

import java.util.ArrayList;
import java.util.List;

public interface ChatService {

    default ChatDto chatEntityToDto(Chat entity) {
        ChatDto dto = ChatDto.builder()
                .chatId(entity.getChatId())
                .chatRoomId(entity.getChatRoom().getChatroomId())
                .senderId(entity.getSender().getMemberId())
                .receiverId(entity.getReceiver().getMemberId())
                .message(entity.getMessage())
                .chatTime(entity.getChatTime())
                .build();
        return dto;
    }

    default ChatRoomDto roomEntityToDto(ChatRoom entity) {
        List<ChatDto> dtoList = new ArrayList<>();
        for (Chat item : entity.getChatList()) {
            dtoList.add(chatEntityToDto(item));
        }

        ChatRoomDto dto = ChatRoomDto.builder()
                .chatroomId(entity.getChatroomId())
                .chatList(dtoList)
                .build();
        return dto;
    }

    default Chat chatDtoToEntity(ChatDto dto) {
        Chat entity = Chat.builder()
                .chatRoom(ChatRoom.builder().chatroomId(dto.getChatRoomId()).build())
                .sender(Member.builder().memberId(dto.getSenderId()).build())
                .receiver(Member.builder().memberId(dto.getReceiverId()).build())
                .message(dto.getMessage())
                .chatTime(dto.getChatTime())
                .build();
        return entity;
    }

    default ChatRoom roomDtoToEntity(ChatRoomDto dto) {
        List<Chat> entityList = new ArrayList<>();
        for (ChatDto item : dto.getChatList()) {
            entityList.add(chatDtoToEntity(item));
        }
        ChatRoom entity = ChatRoom.builder()
                .chatList(entityList)
                .chatroomId(dto.getChatroomId())
                .build();
        return entity;
    }

    /**
     * 보내는 사람,받는 사람 아이디로 chat을 조회해서 없으면 새로운 채팅방 생성해서 해당 아이디 리턴,
     * 있으면 해당 채팅방 리턴
     * @param receiverId
     * @param senderId
     * @return 채팅방ID
     */
    Long createRoom(String receiverId,String senderId);

    /**
     * 보낸(받은) 채팅 DB에 저장
     * @param chat 채팅내용, 보낸사람 아아디, 받는사람 아이디,보낸 시간, 채팅방 아이디
     * @return
     */
    ChatDto chatSave(ChatDto chat);

}
