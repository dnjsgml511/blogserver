package com.portfolio.blog.auth.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.sun.istack.NotNull;

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
	
	@NotNull
	private String id;
	@NotNull
	private String nickname;
	@NotNull
	private String password;
	@NotNull
	private String grade;
	
	@CreationTimestamp
	private LocalDateTime signupdate;
	
	public UserEntity() {
		
	}
	
	public UserEntity(String id, String nickname, String password, String grade){
		this.id = id;
		this.nickname = nickname;
		this.password = password;
		this.grade = grade;
	}
	
}
