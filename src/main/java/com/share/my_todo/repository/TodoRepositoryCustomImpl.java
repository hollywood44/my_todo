package com.share.my_todo.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.share.my_todo.entity.common.TodoProgress;
import com.share.my_todo.entity.member.Member;
import com.share.my_todo.entity.todo.Todo;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.share.my_todo.entity.todo.QTodo.todo1;


public class TodoRepositoryCustomImpl implements TodoRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public TodoRepositoryCustomImpl(EntityManager entityManager) {
        this.queryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public List<Todo> lastWeekTodoListByMemberId(Member memberId, LocalDate mon, LocalDate sun) {
        List<Todo> result =  queryFactory
                .select(todo1)
                .from(todo1)
                .where(
                        todo1.member.eq(memberId)
                        .and(todo1.finishDate.between(mon,sun))
                ).fetch();
        return result;
    }

    @Override
    public List<Todo> findByMemberAndProgress(Member member, TodoProgress todoProgress) {
        List<Todo> result = queryFactory
                .selectFrom(todo1)
                .where(
                        todo1.member.eq(member),todo1.progress.eq(todoProgress)
                ).fetch();
        return result;
    }
}