package com.share.my_todo.DTO.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendListDto {

    private Long friendListId;
    private String memberId;
    private List<FriendDto> friendList;

}
