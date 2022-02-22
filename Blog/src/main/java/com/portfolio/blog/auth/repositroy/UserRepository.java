package com.portfolio.blog.auth.repositroy;

import org.springframework.data.jpa.repository.JpaRepository;

import com.portfolio.blog.auth.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer>{

}
