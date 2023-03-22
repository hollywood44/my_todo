package com.share.my_todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.share.my_todo.DTO.member.MemberLoginRequestDto;
import com.share.my_todo.DTO.todo.TodoDto;
import com.share.my_todo.config.login.TokenInfo;
import com.share.my_todo.service.member.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("TODO Controller 테스트")
class TodoControllerTest {

    private static final String prefix = "/api/todos";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MemberService memberService;

    ObjectMapper objectMapper;
    TokenInfo tokenInfo;


    @BeforeEach
    void setup() {
        this.objectMapper = new ObjectMapper();

//        tokenInfo = memberService.login("test01","1234");
    }

    @Test
    void getNotDoneTodoList() throws Exception{
        if (tokenInfo.getGrantType() != null) {
            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/todos/list")
                            .header(HttpHeaders.AUTHORIZATION,tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken())
                    )
                    .andDo(print())
                    .andExpect(status().isOk());
        }


    }

    @Test
    void getTodoDetail() {
    }

    @Test
    void todoPosting() throws Exception{
        TodoDto posting = TodoDto.builder().todo("test todo").memberId("test01").finishDate("2023-03-15").build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/todos/posting")
                        .content(objectMapper.writeValueAsString(posting))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION,tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken()
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void completeTodo() throws Exception{
        TodoDto todo = TodoDto.builder().todoId(46L).build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/todos/complete")
                        .content(objectMapper.writeValueAsString(todo))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION,tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken()
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void modifyTodo() throws Exception{
        TodoDto modify = TodoDto.builder().todoId(46L).todo("test modify").finishDate("2023-03-16").memberId("test01").build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/todos/modify")
                        .content(objectMapper.writeValueAsString(modify))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION,tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken()
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void delete() throws Exception{
        TodoDto todo = TodoDto.builder().todoId(46L).build();

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/todos/delete")
                        .content(objectMapper.writeValueAsString(todo))
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION,tokenInfo.getGrantType() + " " + tokenInfo.getAccessToken()
                        )
                )
                .andDo(print())
                .andExpect(status().isOk());
    }
}