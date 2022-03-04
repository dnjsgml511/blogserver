package com.portfolio.blog.data.entitiy;

import java.time.LocalDateTime;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.portfolio.blog.config.security.Role;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Schema(description = "사용자(조인)")

@Getter
@Setter
@Entity
@Table(name = "users")
@ToString
public class UserJoinEntity {

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

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "usernum")
	private Collection<BoardEntity> boardlist;
}
