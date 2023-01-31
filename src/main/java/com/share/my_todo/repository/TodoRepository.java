package com.share.my_todo.repository;

import com.share.my_todo.entity.member.Member;
import com.share.my_todo.entity.todo.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<Todo,Long>,TodoRepositoryCustom {

    List<Todo> findAllByMember(Member member);
}
