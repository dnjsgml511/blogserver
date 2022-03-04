package com.portfolio.blog.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.blog.data.entitiy.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
}
