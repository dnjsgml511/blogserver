package com.portfolio.blog.data.entitiy;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name = "board")
@Entity
@Getter @Setter @ToString
public class BoardEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Schema(description = "Index")
	private Integer idx;

	@Schema(description = "Top pick", example = "0")
	private int top;
	@Schema(description = "Title", example = "Title")
	private String title;
	@Schema(description = "Content", example = "Content")
	private String content;
	@Schema(description = "Writer", example = "Writer")
	@Column
	private int usernum;
	
	@CreationTimestamp
	@Schema(description = "Write Date", example = "2022-04-06 00:00:00")
	private LocalDateTime writedate;
	
	@UpdateTimestamp
	@Schema(description = "Update Date" , example = "2022-04-06 00:00:00")
	private LocalDateTime updatedate;
	
	@Schema(description = "Content Hiden", example = "0")
	private int hide;
}
