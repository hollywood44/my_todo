package com.share.my_todo.entity.chat;

import com.share.my_todo.entity.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table(name = "chat_tbl")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chatId;

    @ManyToOne
    @JoinColumn(name = "chatroom_id")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "sender_member_id")
    private Member sender;

    @ManyToOne
    @JoinColumn(name = "receiver_member_id")
    private Member receiver;

    private String message;

    private LocalDateTime chatTime;
}
