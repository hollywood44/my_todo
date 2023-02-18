package com.share.my_todo.DTO.chat;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class ChatRoomDto {

    private Long chatroomId;

    @Builder.Default
    private List<ChatDto> chatList = new ArrayList<>();

    private String memberOneId;
    private String memberTwoId;
}
