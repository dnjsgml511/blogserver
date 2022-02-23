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
import lombok.ToString;

@Getter
@Setter
@Table(name = "users")
@Entity
@ToString
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
	
	public UserEntity() {
		
	}
	
	public UserEntity(String id, String nickname, String password){
		this.id = id;
		this.nickname = nickname;
		this.password = password;
		this.grade = "사용자";
	}
	
	public UserEntity(String id, String nickname, String password, String grade){
		this.id = id;
		this.nickname = nickname;
		this.password = password;
		this.grade = grade;
	}
	
}
