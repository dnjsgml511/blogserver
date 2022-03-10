package com.portfolio.blog.data.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.portfolio.blog.config.security.JwtTokenUtil;
import com.portfolio.blog.data.dto.auth.UserControllResponse;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.data.service.AdminService;
import com.portfolio.blog.util.ReturnText;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JwtTokenUtil jwtTokenUtil;

	@Override
	public String userControll(UserControllResponse response, HttpServletRequest request) throws Exception {

		// 사용자 번호로 유저 정보 가져오기
		UserEntity user = userRepository.findById(response.getNum()).orElseThrow(
				() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, ReturnText.CHECK_USER.getValue()));
		
		// 등급 변경
		if(response.getGrade() != null) {
			if(request.isUserInRole("ROLE_ADMIN")) {
				user.setGrade(response.getGrade());
			}else {
				throw new ResponseStatusException(HttpStatus.FORBIDDEN, ReturnText.NOT_HAVE_GRADE.getValue());
			}
		}
		
		// 승인, 블락 변경
		if(response.getActive() != null) {
			user.setActive(response.getActive());
		}

		userRepository.save(user);
		return user.getNickname() + ReturnText.USER_UPDATE.getValue();
	}
	
//	@Override
//	public List<UserEntity> userlist(UserEntity userEntity) throws Exception {
//		
//		if (userEntity.getId() != null && !userEntity.getId().equals("")) {
//			return userRepository.findByIdLike("%" + userEntity.getId() + "%");
//		} else if (userEntity.getNickname() != null && !userEntity.getNickname().equals("")) {
//			return userRepository.findByNicknameLike("%" + userEntity.getNickname() + "%");
//		} else if (userEntity.getGrade() != null && !userEntity.getGrade().equals("")) {
//			return userRepository.findByGrade(userEntity.getGrade());
//		}
//		return userRepository.findAll();
//	}
//
//	@Override
//	public UserEntity userselect(UserEntity userEntity) throws Exception {
//		
//		String id = userEntity.getId();
//		id = id == null ? "" : id;
//		
//		if(id.equals("")) {
//			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ReturnText.SELECT_FAIL.getValue());
//		}
//		
//		UserEntity user = userRepository.findById(id);
//		if(user == null) {
//			throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, ReturnText.SELECT_FAIL.getValue());
//		}
//		
//		return user;
//	}

}
