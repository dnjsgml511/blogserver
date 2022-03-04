//package com.portfolio.blog.data.service.impl;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//import org.springframework.web.server.ResponseStatusException;
//
//import com.portfolio.blog.config.security.JwtTokenUtil;
//import com.portfolio.blog.data.entitiy.UserEntity;
//import com.portfolio.blog.data.repository.UserRepository;
//import com.portfolio.blog.data.service.AdminService;
//import com.portfolio.blog.util.ReturnText;
//
//@Service
//public class AdminServiceImpl implements AdminService {
//
//	@Autowired
//	UserRepository userRepository;
//	
//	@Autowired
//	JwtTokenUtil jwtTokenUtil;
//
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
//
//}
