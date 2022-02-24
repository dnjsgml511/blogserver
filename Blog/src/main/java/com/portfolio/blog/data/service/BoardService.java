package com.portfolio.blog.data.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.portfolio.blog.data.entitiy.BoardEntity;

public interface BoardService {

	// 게시물 내용 가져오기
	public Page<BoardEntity> findByBoard(Pageable pageable, String search) throws Exception;
	
	// 상단 고정 게시물 내용 가져오기
	public Page<BoardEntity> findByTopBoard(Pageable pageable) throws Exception;
	
	// 게시물 작성
	public void saveBoard(BoardEntity boardEntity) throws Exception;
	
	// 게시물 수정
	public void updateBoard(BoardEntity boardEntity) throws Exception;
	
	// 게시물 삭제
	public void deleteBoard(BoardEntity boardEntity) throws Exception;
	
	// 게시물 숨기기
	public void hideBoard(BoardEntity boardEntity) throws Exception;
	
	// 게시물 상단 고정
	public void topPickBoard(BoardEntity boardEntity) throws Exception;
	
}
