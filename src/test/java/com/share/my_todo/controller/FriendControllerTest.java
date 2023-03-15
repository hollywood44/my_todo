package com.share.my_todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.share.my_todo.config.login.TokenInfo;
import com.share.my_todo.service.friend.FriendService;
import com.share.my_todo.service.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.ServerResponse.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("FRIEND CONTROLLER TEST")
class FriendControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    ObjectMapper objectMapper;
    TokenInfo tokenInfo;
    String tokenValue;

    private static final String prefix = "/api/friends/";

    @BeforeEach
    void setup() {
        this.objectMapper = new ObjectMapper();

        tokenInfo = memberService.login("test01","1234");
        tokenValue = tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken();
    }

    @Test
    @DisplayName("팔로우 요청")
    @Rollback(value = false)
    void followRequest() throws Exception{
        Map<String, String> requestMap = new HashMap<>();
        requestMap.put("followId", "test01");

        mockMvc.perform(MockMvcRequestBuilders
                        .post(prefix + "/follow-request")
                        .content(objectMapper.writeValueAsString(requestMap))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, tokenValue))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("언팔로우")
    void unfollow() throws Exception{

    }

    @Nested
    @DisplayName("팔로우 요청에 따른 기능 테스트")
    class followToUnfollow {

        @Test
        @DisplayName("팔로우 수락")
        void followAccept() throws Exception{
            Map<String, String> requestMap = new HashMap<>();
            requestMap.put("followerId", "member2");

            mockMvc.perform(MockMvcRequestBuilders
                            .post(prefix + "/follow-accept")
                            .content(objectMapper.writeValueAsString(requestMap))
                            .contentType(MediaType.APPLICATION_JSON)
                            .header(HttpHeaders.AUTHORIZATION, tokenValue))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("팔로우 거절")
        void followReject() throws Exception{
            Map<String, String> requestMap = new HashMap<>();
            requestMap.put("followerId", "member2");

            mockMvc.perform(MockMvcRequestBuilders
                            .post(prefix + "/follow-reject")
                            .content(objectMapper.writeValueAsString(requestMap))
                            .contentType(MediaType.APPLICATION_JSON)
                            .header(HttpHeaders.AUTHORIZATION, tokenValue))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

    }

    @Nested
    @DisplayName("리스트 기능 테스트")
    class getLists {
        @Test
        @DisplayName("친구 목록 불러오기")
        void getFriendList() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders
                            .get(prefix + "/list")
                            .header(HttpHeaders.AUTHORIZATION, tokenValue))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("요청받은 팔로우 목록 불러오기")
        void followRequestedListPage() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders
                            .get(prefix + "/requested-list")
                            .header(HttpHeaders.AUTHORIZATION, tokenValue))
                    .andDo(print())
                    .andExpect(status().isOk());
        }

        @Test
        @DisplayName("요청한 팔로우 목록 불러오기")
        void followRequestList() throws Exception{
            mockMvc.perform(MockMvcRequestBuilders
                            .get(prefix + "/request-list")
                            .header(HttpHeaders.AUTHORIZATION, tokenValue))
                    .andDo(print())
                    .andExpect(status().isOk());
        }
    }

}