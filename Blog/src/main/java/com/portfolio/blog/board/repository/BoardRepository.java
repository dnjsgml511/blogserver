package com.portfolio.blog.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.portfolio.blog.board.entity.BoardEntity;

@Repository
public interface BoardRepository extends PagingAndSortingRepository<BoardEntity, Integer>{
	public Page<BoardEntity> findByTitleLike(String search, Pageable pageable);
}