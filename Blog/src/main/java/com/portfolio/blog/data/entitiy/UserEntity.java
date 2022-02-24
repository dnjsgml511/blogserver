package com.portfolio.blog.data.entitiy;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.portfolio.blog.config.security.Role;

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
	
	@Enumerated(EnumType.STRING)
	private Role grade;
	
	@CreationTimestamp
	private LocalDateTime signupdate;
	
	public UserEntity() {
		
	}
	
	public UserEntity(String id, String password){
		this.id = id;
		this.password = password;
	}
	
	
	public UserEntity(String id, String nickname, String password){
		this.id = id;
		this.nickname = nickname;
		this.password = password;
		this.grade = Role.ROLE_USER;
	}
	
	public UserEntity(String id, String nickname, String password, Role grade){
		this.id = id;
		this.nickname = nickname;
		this.password = password;
		this.grade = grade;
	}
	
	@SuppressWarnings("unused")
	public UserEntity(UserEntity userEntity) {
		this.id = userEntity.getId();
		this.nickname = userEntity.getNickname();
		this.password = userEntity.getPassword();
		if(userEntity.getGrade() == null) {
			this.grade = Role.ROLE_USER;
		}else {
			this.grade = userEntity.getGrade();
		}
	}
	
	
}
