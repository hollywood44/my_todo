package com.share.my_todo.service;

import com.share.my_todo.DTO.member.MemberDto;
import com.share.my_todo.entity.common.Auth;
import com.share.my_todo.repository.member.MemberRepository;
import com.share.my_todo.service.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    @Rollback(value = false)
    void signUp(){
//        MemberDto dto = MemberDto.builder()
//                .memberId("admin")
//                .password("1234")
//                .phone("010-0000-0000")
//                .email("admin@sharetodo.com")
//                .name("admin")
//                .auth(Auth.Admin)
//                .build();

        MemberDto dto = MemberDto.builder()
                .memberId("member8")
                .password("1234")
                .name("member8")
                .auth(Auth.Member)
                .build();

        System.out.println(memberService.signUp(dto));
    }

    @Test
    void getMemberByMemberId() {
        System.out.println(memberService.getMyInfo("admin"));
    }

    @Test
    @Rollback(value = false)
    void modifyMemberInfo() {
        MemberDto dto = MemberDto.builder()
                .memberId("member1")
                .name("member1-modified1")
                .auth(Auth.Member)
                .build();

        System.out.println(memberService.getMyInfo("member1"));
    }

    @Test
    @Rollback(value = false)
    void modifyPassword() {
        memberService.modifyPassword("member1", "12345");
    }
}