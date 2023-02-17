package com.share.my_todo.repository.friend;

import com.share.my_todo.entity.member.FriendList;
import com.share.my_todo.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendListRepository extends JpaRepository<FriendList,Long>, FriendListRepositoryCustom {
    Optional<FriendList> findByMember(Member member);
}
