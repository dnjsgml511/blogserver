package com.portfolio.blog.board.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "board")
@Entity
public class BoardEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idx;
	
	private String title;
	private String content;
	private String writer;
	private Date writedate;
	
}
