package com.share.my_todo.repository;

import com.share.my_todo.entity.member.Member;
import com.share.my_todo.entity.notice.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice,Long> {

    List<Notice> findAllByMemberAndReadAtIsNull(Member member);
}
