package com.share.my_todo.repository;

import com.share.my_todo.entity.member.Friend;
import com.share.my_todo.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend,Long> {
    Optional<Friend> findByMember(Member member);
}
