package com.share.my_todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.share.my_todo.DTO.member.MemberDto;
import com.share.my_todo.entity.common.Auth;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.service.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Member Controller 테스트")
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("로그인 테스트")
    void login() throws Exception{
        Member member = Member.builder().memberId("test01").password("1234").auth(Auth.MEMBER).build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/members/sign-in")
                        .content(objectMapper.writeValueAsString(member))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void signUp()throws Exception {
        MemberDto member = MemberDto.builder().memberId("test02").password("1234").name("test02").build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/members/sign-up")
                        .content(objectMapper.writeValueAsString(member))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void idCheck() {
    }

    @Test
    void getMemberInfo() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/members/info")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MDEiLCJhdXRoIjoiUk9MRV9NRU1CRVIiLCJleHAiOjE2Nzg4MTgzMjV9.7D0xpUyhObD8yQunw56WU2h82aKyJ_Wm13AgjbCh7GE"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void modifyMemberInfo() {
    }

    @Test
    void modifyPassword() {
    }
}