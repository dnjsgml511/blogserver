package com.portfolio.blog.board.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.blog.board.entity.BoardEntity;
import com.portfolio.blog.board.service.BoardService;

@RestController
@RequestMapping("/board")
@Configuration
public class BoardController {

	public BoardController(@RequestBody BoardService boardService) {
		this.boardService = boardService;
	}
	
	BoardService boardService;
	
	@PostMapping("insert")
	public HttpStatus insertBoard(BoardEntity boardEntity) throws Exception{
		boardService.saveBoard(boardEntity);
		return HttpStatus.OK;
	}
	
	@PostMapping("search")
	public ResponseEntity<?> searchBoard(Pageable pageable, @RequestParam("search") String search) throws Exception{
		return new ResponseEntity<>(boardService.findByBoard(pageable, search), HttpStatus.OK);
	}
	
}
