package com.share.my_todo.repository;

import com.share.my_todo.entity.member.FriendList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendListRepository extends JpaRepository<Long, FriendList> {
}
