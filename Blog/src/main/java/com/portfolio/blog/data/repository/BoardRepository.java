package com.portfolio.blog.data.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.blog.data.entitiy.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
	public Page<BoardEntity> findByTitleLikeAndTopAndHideAndWriterLike(String search, int top, int hide, String writer, Pageable pageable);
	public Page<BoardEntity> findByTitleLikeAndTopAndHide(String search, int top, int hide, Pageable pageable);
	public Page<BoardEntity> findByTop(int top, Pageable pageable);
}
