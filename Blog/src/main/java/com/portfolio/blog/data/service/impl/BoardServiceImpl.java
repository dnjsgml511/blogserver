package com.portfolio.blog.data.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.portfolio.blog.config.security.JwtTokenUtil;
import com.portfolio.blog.config.security.Role;
import com.portfolio.blog.data.dto.board.InsertBoardResponse;
import com.portfolio.blog.data.entitiy.BoardEntity;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.BoardRepository;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.data.service.BoardService;
import com.portfolio.blog.util.ReturnText;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardRepository boardRepository;
	@Autowired
	UserRepository userRepositroy;
	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Override
	public String insertBoard(InsertBoardResponse response, HttpServletRequest request) throws Exception {

		// 등록자 고유번호
		int usernum = Integer.parseInt(jwtTokenUtil.popJWTData(request, "jti"));

		// 사용자 존제 체크
		UserEntity user = userRepositroy.findById(usernum).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_USER.getValue()));

		// 사용자 블락일 경우
		if (user.getActive() == 0) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_ACTIVE.getValue());
		}

		// 타이틀 체크
		if (response.getTitle() == null || response.getTitle().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_DATA.getValue());
		}

		response.setNum(usernum);
		boardRepository.save(new BoardEntity(response));

		return ReturnText.SAVE_SUCCESS.getValue();
	}

	@Override
	public String deleteBoard(int num, HttpServletRequest request) throws Exception {

		BoardEntity board = boardRepository.findById(num).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_IDX.getValue()));
		
		// 게시판 작성자 등급
		Role writerGrade = board.getUser().getGrade();
		// 게시판 작성자 num
		int writerNum = board.getUser().getNum();
		
		// 수정자 등급
		Role updaterGrade;
		if (request.isUserInRole("ROLE_ADMIN")) {
			updaterGrade = Role.ROLE_ADMIN;
		} else if (request.isUserInRole("ROLE_MANAGER")) {
			updaterGrade = Role.ROLE_MANAGER;
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, ReturnText.NOT_HAVE_GRADE.getValue());
		}
		// 수정자 num
		int updaterNum = Integer.parseInt(jwtTokenUtil.popJWTData(request, "jti"));
		
		System.out.println(writerNum);
		System.out.println(updaterNum);

		if (updaterGrade == Role.ROLE_ADMIN) {
			boardRepository.delete(board);
		} else if (updaterGrade == Role.ROLE_MANAGER && writerGrade != Role.ROLE_ADMIN) {
			boardRepository.delete(board);
		} else {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN, ReturnText.NOT_HAVE_GRADE.getValue());
		}

		return ReturnText.DELETE_SUCCESS.getValue();
	}

}
