package com.portfolio.blog.auth.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "entity")
@Entity
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int num;
	private String id;
	private String nickname;
	private String password;
	private String grade;
	
	@CreationTimestamp
	private LocalDateTime signupdate;
	
	public UserEntity(String id, String nickname, String password, String grade){
		this.id = id;
		this.nickname = nickname;
		this.password = password;
		this.grade = grade;
	}
	
}
