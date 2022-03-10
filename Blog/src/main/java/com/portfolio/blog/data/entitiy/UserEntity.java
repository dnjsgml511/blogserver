package com.portfolio.blog.data.entitiy;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.portfolio.blog.config.security.Role;
import com.portfolio.blog.data.dto.auth.SignupResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "사용자(조인)")

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "USER_SEQ_GENERATOR", sequenceName = "USER_SEQ", initialValue = 1, allocationSize = 1)
public class UserEntity {

	@Id
	@Schema(description = "Own Number")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SEQ_GENERATOR")
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

	public UserEntity(SignupResponse response) {
		this.id = response.getId();
		this.password = response.getPassword();
		this.nickname = response.getNickname();
		this.active = 0;
		this.grade = Role.ROLE_USER;
	}

	public UserEntity(int num, String id, String password, String nickname, int active, Role role) {
		this.num = num;
		this.id = id;
		this.password = password;
		this.nickname = nickname;
		this.active = active;
		this.grade = role;
	}

	public UserEntity(int num, String id, String password, String nickname, Role role) {
		this.num = num;
		this.id = id;
		this.password = password;
		this.nickname = nickname;
		this.grade = role;
	}

	public UserEntity(String id, String password, String nickname, Role role) {
		this.id = id;
		this.password = password;
		this.nickname = nickname;
		this.grade = role;
	}
	
	public UserEntity(int num, String id, String password, String nickname, Role role, int active) {
		this.num = num;
		this.id = id;
		this.password = password;
		this.nickname = nickname;
		this.active = active;
		this.grade = role;
	}

	public UserEntity(String id, String password, String nickname, Role role, int active) {
		this.id = id;
		this.password = password;
		this.nickname = nickname;
		this.active = active;
		this.grade = role;
	}

	public UserEntity(String id, String password, String nickname) {
		this.id = id;
		this.password = password;
		this.nickname = nickname;
		this.active = 0;
		this.grade = Role.ROLE_USER;
	}

	public UserEntity(String id, String password, String nickname, int active) {
		this.id = id;
		this.password = password;
		this.nickname = nickname;
		this.active = active;
		this.grade = Role.ROLE_USER;
	}

	public UserEntity(int num) {
		this.num = num;
	}
	
	public UserEntity(UserEntity entity) {
		this.id = entity.getId();
		this.password = entity.getPassword();
		this.nickname = entity.getNickname();
		this.active = entity.getActive();
		this.grade = entity.getGrade();
		this.signupdate = entity.getSignupdate();
	}
}
