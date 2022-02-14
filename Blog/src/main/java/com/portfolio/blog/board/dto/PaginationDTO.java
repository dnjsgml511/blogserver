package com.portfolio.blog.board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationDTO {

	private int page;
	private int start;
	private int end;
	private int block;

	private int rows;

	public void getRownums(int page, int rows, int block) {
		this.page = page;
		this.rows = rows;
		this.block = block;
		this.start = (page - 1) * block;
		this.end = page * block > rows ? rows : page * block;
	}

}
