package com.share.my_todo.service;

import com.share.my_todo.DTO.member.MemberDto;
import com.share.my_todo.entity.member.Member;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface MemberService extends UserDetailsService {

    default Member dtoToEntity(MemberDto dto) {
        Member entity = Member.builder()
                .memberId(dto.getMemberId())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .name(dto.getName())
                .auth(dto.getAuth())
                .build();
        return entity;
    }

    /**
     * 회원가입
     * @param memberDto
     * @return String타입 memberId
     */
    String SignUp(MemberDto memberDto);
}
