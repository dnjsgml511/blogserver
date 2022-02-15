package com.portfolio.blog.board.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "board")
@Entity
@Data
@NoArgsConstructor
public class BoardEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idx;

	private int top;
	private String title;
	private String content;
	private String writer;
	private String writedate;

	public BoardEntity(String title, String content, String writer) {
		this.title = title;
		this.content = content;
		this.writer = writer;
	}

}
