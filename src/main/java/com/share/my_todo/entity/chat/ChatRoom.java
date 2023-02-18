package com.share.my_todo.entity.chat;

import com.share.my_todo.entity.member.Member;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table(name = "chatroom_tbl")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long chatroomId;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Chat> chatList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_one_id")
    private Member memberOneId;

    @ManyToOne
    @JoinColumn(name = "member_two_id")
    private Member memberTwoId;
}
