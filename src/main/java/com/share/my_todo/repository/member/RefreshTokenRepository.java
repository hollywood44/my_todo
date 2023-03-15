package com.share.my_todo.repository.member;

import com.share.my_todo.entity.member.Member;
import com.share.my_todo.entity.member.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,String> {
}
