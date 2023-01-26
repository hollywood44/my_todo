package com.share.my_todo.repository;

import com.share.my_todo.entity.notice.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Long, Notice> {
}
