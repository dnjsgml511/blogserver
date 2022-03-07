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
import com.portfolio.blog.data.dto.SignupResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "사용자")

@Getter @Setter @Entity @ToString
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

	@Id
	@Schema(description = "Own Number")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int num;

	@Schema(description = "ID")
	private String id;
	@Schema(description = "NickName")
	private String nickname;
	@Schema(description = "Password")
	private String password;
	@Schema(description = "Active")
	private int active;

	@Enumerated(EnumType.STRING)
	@Schema(description = "Grade")
	private Role grade;

	@CreationTimestamp
	@Schema(description = "Signup Date")
	private LocalDateTime signupdate;
	
	public UserEntity(String id, String password, String nickname) {
		this.id = id;
		this.password = password;
		this.nickname = nickname;
	}
	
	public UserEntity(SignupResponse response){
		this.id = response.getId();
		this.password = response.getPassword();
		this.nickname = response.getNickname();
		this.active = 0;
		this.grade = Role.ROLE_ADMIN;
	}
}
