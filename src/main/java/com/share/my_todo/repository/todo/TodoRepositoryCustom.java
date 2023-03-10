package com.share.my_todo.repository.todo;

import com.share.my_todo.entity.common.TodoProgress;
import com.share.my_todo.entity.member.FriendList;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.entity.todo.Todo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TodoRepositoryCustom {

    List<Todo> lastWeekTodoListByMemberId(Member memberId, LocalDate mon,LocalDate sun);

    List<Todo> findByMemberAndProgress(Member member, TodoProgress todoProgress);
}
