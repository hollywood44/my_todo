package com.share.my_todo.DTO.member;

import com.share.my_todo.entity.member.FriendList;
import com.share.my_todo.entity.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendDto {

    private Long friendId;
    private Long friendListId;
    private String memberId;
}
