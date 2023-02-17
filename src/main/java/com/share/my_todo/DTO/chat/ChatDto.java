package com.share.my_todo.DTO.chat;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ChatDto {

    private Long chatId;
    private Long chatRoomId;
    private String senderId;
    private String receiverId;
    private String message;
    private LocalDateTime chatTime;
}
