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

	@Autowired BoardRepository boardRepository;
	@Autowired UserRepository userRepositroy;
	@Autowired JwtTokenUtil jwtTokenUtil;

	@Override
	public String insertBoard(InsertBoardResponse response, HttpServletRequest request) throws Exception {

		// 사용자 번호 체크
		if (response.getNum() == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_USER.getValue());
		}

		// 사용자 존제 체크
		UserEntity user = userRepositroy.findById(response.getNum()).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_USER.getValue()));

		// 사용자와 토큰의 사용자 비교
		String userid = user.getId();
		String id = jwtTokenUtil.popJWTData(request, "jti");
		if(!userid.equals(id)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_USER.getValue());
		}

		// 타이틀 체크
		if (response.getTitle() == null || response.getTitle().equals("")) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_DATA.getValue());
		}

		response.setWriter(user.getId());
		boardRepository.save(new BoardEntity(response));

		return ReturnText.SAVE_SUCCESS.getValue();
	}

	@Override
	public String deleteBoard(int num, HttpServletRequest request) throws Exception {
		
		BoardEntity board = boardRepository.findById(num).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_IDX.getValue()));
			
//			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.NOT_HAVE_GRADE.getValue());
		
		System.out.println("_______________ DELETE START__________________");
		System.out.println(board);
		System.out.println("_______________ DELETE START__________________");
		
		
		
		if(request.isUserInRole("ROLE_ADMIN")) {
			boardRepository.delete(board);
		}else if(request.isUserInRole("ROLE_MANAGER")) {
			if(board.getUser().getGrade().equals("ROLE_ADMIN")) {
				boardRepository.delete(board);
			}else {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.NOT_HAVE_GRADE.getValue());
			}
		}else if(request.isUserInRole("ROLE_USER")) {
			if(board.getUser().getGrade().equals("ROLE_ADMIN") || board.getUser().getGrade().equals("ROLE_MANAGER")) {
				
			}
		}
		
		
		System.out.println("_______________ DELETE END__________________");
		System.out.println(board);
		System.out.println("_______________ DELETE END__________________");
		
		boardRepository.delete(board);
		
		return ReturnText.DELETE_SUCCESS.getValue();
	}

}
