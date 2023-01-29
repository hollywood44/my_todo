package com.share.my_todo.entity.member;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 친구목록 고유아이디, 친구목록 소유자 아이디, 친구목록 리스트
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table(name = "friend_list_tbl")
public class FriendList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendListId;

    @ManyToOne(targetEntity = Member.class,fetch = FetchType.LAZY)
    @JoinColumn(name = "memberId")
    private Member member;

    @OneToMany(mappedBy = "friendList", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @Builder.Default
    private List<Friend> friendList = new ArrayList<>();

    public void addFriend(Friend friend) {
        friend.setFriendList(this);
        this.friendList.add(friend);
    }
}
