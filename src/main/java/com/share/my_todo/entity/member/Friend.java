package com.share.my_todo.entity.member;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table(name = "friend_tbl")
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendId;

    @ManyToOne(targetEntity = FriendList.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "friendListId")
    private FriendList friendList;

    @ManyToOne(targetEntity = Member.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;
}
