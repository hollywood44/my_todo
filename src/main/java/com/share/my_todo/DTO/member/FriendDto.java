package com.share.my_todo.DTO.member;

import com.share.my_todo.entity.member.FriendList;
import com.share.my_todo.entity.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 고유아이디, 친구아이디, 팔로우상태, 소속된 친구목록 아이디
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FriendDto {

    private Long friendId;
    private String memberId;
    private String Status;
    private Long friendListId;
    private int thisWeekRate;
    private int lastWeekRate;

}
