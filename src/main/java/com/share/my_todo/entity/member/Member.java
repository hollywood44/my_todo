package com.share.my_todo.entity.member;

import com.share.my_todo.entity.common.Auth;
import com.share.my_todo.entity.common.CommonTime;
import jakarta.persistence.*;
import lombok.*;

/**
 * 회원 Entity <br/>
 * 아이디, 패스워드, 이름, 이메일, 폰번호, 권한을 멤버로 가진다.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table(name = "member_tbl")
public class Member extends CommonTime {

    @Id
    @Column(length = 20)
    private String memberId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    private Auth auth;


}
