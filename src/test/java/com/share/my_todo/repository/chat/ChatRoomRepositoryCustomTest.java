package com.share.my_todo.repository.chat;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ChatRoomRepositoryCustomTest {

    @Autowired
    private ChatRoomRepositoryCustomImpl chatRoomRepositoryCustomImpl;

    @Test
    void checkChatAuthority() {
        boolean exists = chatRoomRepositoryCustomImpl.checkChatAuthority("test01",4L);
        System.out.println(exists);
    }
}