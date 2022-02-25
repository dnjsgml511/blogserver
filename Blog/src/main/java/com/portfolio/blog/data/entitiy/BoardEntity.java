package com.portfolio.blog.data.entitiy;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "board")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class BoardEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Schema(description = "Index")
	private int idx;

	@Schema(description = "Top pick")
	private int top;
	@Schema(description = "Title")
	private String title;
	@Schema(description = "Content")
	private String content;
	@Schema(description = "Writer")
	private String writer;
	
	@CreationTimestamp
	@Schema(description = "Write Date")
	private LocalDateTime writedate;
	
	@UpdateTimestamp
	@Schema(description = "Update Date")
	private LocalDateTime updatedate;
	
	@Schema(description = "Content Hiden")
	private int hide;
	
	public BoardEntity(int idx) {
		this.idx = idx;
	}

	public BoardEntity(String title, String content, String writer) {
		this.title = title;
		this.content = content;
		this.writer = writer;
	}

}
