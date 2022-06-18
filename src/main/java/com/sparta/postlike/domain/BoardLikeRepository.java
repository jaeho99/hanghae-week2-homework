package com.sparta.postlike.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    BoardLike findByBoardIdAndUserId(Long boardId, Long userId);
    int countByBoardId(Long boardId);
    List<BoardLike> findAllByBoardId(Long boardId);
}
