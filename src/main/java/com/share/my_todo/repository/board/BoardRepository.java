package com.share.my_todo.repository.board;

import com.share.my_todo.entity.board.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board,Long>{

    Page<Board> findAll(Pageable pageable);
    Page<Board> findAllByParentIdIsNull(Pageable pageable);

    Board findByParentId(Long parentId);
}
