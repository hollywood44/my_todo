package com.share.my_todo.entity.member;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "friendId", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Friend> friendList = new ArrayList<>();
}
