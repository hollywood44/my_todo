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
    void signUpForAdmin() {
        MemberDto admin = MemberDto.builder().memberId("admin01").password("1234").name("admin01").build();
        memberService.signUpForAdmin(admin);
    }

    @Test
    @Rollback(value = false)
    void signUp(){
    }

    @Test
    void getMemberByMemberId() {
        System.out.println(memberService.getMyInfo("admin"));
    }

    @Test
    @Rollback(value = false)
    void modifyMemberInfo() {
    }

    @Test
    @Rollback(value = false)
    void modifyPassword() {
    }
}