package com.portfolio.blog.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.blog.data.entitiy.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
	public UserEntity findById(String id) throws Exception;
	public UserEntity findByNickname(String nickname) throws Exception;
	public UserEntity findByEmail(String email) throws Exception;
	
	public List<UserEntity> findByIdOrNicknameOrEmailOrCompanyno(String id, String nickname, String email, String companyno);
}
