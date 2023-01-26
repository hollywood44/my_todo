package com.share.my_todo.repository;

import com.share.my_todo.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<String, Member> {
}
