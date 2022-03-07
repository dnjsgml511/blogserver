package com.portfolio.blog.data.service;

import javax.servlet.http.HttpServletRequest;

import com.portfolio.blog.data.dto.board.InsertBoardResponse;

public interface BoardService {

	// 게시물 등록
	public String insertBoard(InsertBoardResponse response, HttpServletRequest request) throws Exception;

}
