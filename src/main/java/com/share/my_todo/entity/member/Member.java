package com.share.my_todo.entity.member;

import com.share.my_todo.DTO.member.MemberDto;
import com.share.my_todo.entity.common.Auth;
import com.share.my_todo.entity.common.CommonTime;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

/**
 * 회원 Entity <br/>
 * 아이디, 패스워드, 이름, 권한을 멤버로 가진다.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Table(name = "member_tbl")
public class Member extends CommonTime implements UserDetails {

    @Id
    @Column(length = 20)
    private String memberId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;


    @Enumerated(EnumType.STRING)
    private Auth auth;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @Builder.Default
    private List<FriendList> friendList = new ArrayList<>();


    public static Member easyMakeMember(String id) {
        return Member.builder().memberId(id).build();
    }
    public void modifyInfo(MemberDto dto) {
        this.name = dto.getName();
    }

    public void modifyPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : auth.getValue().split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }

    @Override
    public String getUsername() {
        return memberId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
