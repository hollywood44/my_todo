package com.share.my_todo.repository;

import com.share.my_todo.entity.member.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Long, Friend> {
}
