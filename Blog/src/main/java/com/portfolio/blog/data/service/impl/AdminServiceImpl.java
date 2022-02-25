package com.portfolio.blog.data.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.repository.UserRepository;
import com.portfolio.blog.data.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	UserRepository userRepository;

	@Override
	public List<UserEntity> userlist(UserEntity userEntity) throws Exception {
		
		if (userEntity.getId() != null && !userEntity.getId().equals("")) {
			return userRepository.findByIdLike("%" + userEntity.getId() + "%");
		} else if (userEntity.getNickname() != null && !userEntity.getNickname().equals("")) {
			return userRepository.findByNicknameLike("%" + userEntity.getNickname() + "%");
		} else if (userEntity.getGrade() != null && !userEntity.getGrade().equals("")) {
			return userRepository.findByGrade(userEntity.getGrade());
		}
		return userRepository.findAll();
	}

}
