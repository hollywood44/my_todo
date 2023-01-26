package com.share.my_todo.repository;

import com.share.my_todo.entity.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Long, Board> {
}
