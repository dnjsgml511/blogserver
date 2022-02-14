package com.portfolio.blog.board.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Table(name = "board")
@Entity
@Data
public class BoardEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idx;

	private int top;
	private String title;
	private String content;
	private String writer;
	private String writedate;

}
