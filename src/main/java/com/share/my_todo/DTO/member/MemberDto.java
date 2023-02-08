package com.share.my_todo.DTO.member;

import com.share.my_todo.entity.common.Auth;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {

    private String memberId;
    private String password;
    private String name;
    private Auth auth;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

}
