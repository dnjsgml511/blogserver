package com.portfolio.blog.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.blog.config.security.Role;
import com.portfolio.blog.data.entitiy.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	public UserEntity findById(String id) throws Exception;
	public List<UserEntity> findByIdLike(String id) throws Exception;
	public List<UserEntity> findByNicknameLike(String nickname) throws Exception;
	public List<UserEntity> findByGrade(Role grade) throws Exception;
}
