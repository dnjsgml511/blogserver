package com.portfolio.blog.data.entitiy;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.portfolio.blog.data.dto.board.InsertBoardResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "board")
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = "BOARD_SEQ_GENERATOR", sequenceName = "BOARD_SEQ", initialValue = 1, allocationSize = 1)
public class BoardEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BOARD_SEQ_GENERATOR")
	@Schema(description = "Index")
	private int idx;

	@Schema(description = "Top pick", example = "0")
	private int top;
	@Schema(description = "Title", example = "Title")
	private String title;
	@Schema(description = "Content", example = "Content")
	private String content;
	@Schema(description = "Writer", example = "Writer")
	private String writer;

	@ManyToOne
	@JoinColumn(name = "num")
	UserEntity user;

	@CreationTimestamp
	@Schema(description = "Write Date", example = "2022-04-06 00:00:00")
	private LocalDateTime writedate;

	@UpdateTimestamp
	@Schema(description = "Update Date", example = "2022-04-06 00:00:00")
	private LocalDateTime updatedate;

	@Schema(description = "Content Hiden", example = "0")
	private int hide;

	public BoardEntity(InsertBoardResponse response) {
		this.title = response.getTitle();
		this.content = response.getContent();
	}

	public BoardEntity(String title, String content, String writer, UserEntity user) {
		this.title = title;
		this.content = content;
		this.writer = writer;
		this.user = user;
	}

	public BoardEntity(int idx, UserEntity user) {
		this.idx = idx;
		this.user = user;
	}

	public BoardEntity(int idx, String title, String content, int num, String writer) {
		this.idx = idx;
		this.title = title;
		this.content = content;
		this.writer = writer;
	}

	public BoardEntity(String title, String content, String writer) {
		this.title = title;
		this.content = content;
		this.writer = writer;
	}

	public BoardEntity(int idx) {
		this.idx = idx;
	}
}
