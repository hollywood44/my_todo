package com.share.my_todo.entity.member;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table(name = "refresh_token_tbl")
public class RefreshToken {

    @Id
    private String memberId;

    private String refreshToken;
}
