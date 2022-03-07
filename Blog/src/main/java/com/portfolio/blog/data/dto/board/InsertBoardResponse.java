package com.portfolio.blog.data.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InsertBoardResponse {
	private String title;
	private String content;
	private String writer;
	private Integer num;

	public InsertBoardResponse(String title, String content, Integer num) {
		this.title = title;
		this.content = content;
		this.num = num;
	}
}
