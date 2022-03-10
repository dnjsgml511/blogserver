package com.portfolio.blog.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.blog.data.entitiy.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	public UserEntity findById(String id) throws Exception;
	public UserEntity findByNickname(String nickname) throws Exception;
}
