package com.portfolio.blog.board.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
	public ResponseEntity<?> insertBoard(BoardEntity boardEntity) throws Exception {
		boardService.saveBoard(boardEntity);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("topsearch")
	public ResponseEntity<?> topSearchBoard(@PageableDefault(size = 3, page = 0) Pageable pageable) throws Exception {
		return new ResponseEntity<>(boardService.findByTopBoard(pageable), HttpStatus.OK);
	}

	@GetMapping("search")
	public ResponseEntity<?> searchBoard(@PageableDefault(size = 15) Pageable pageable,
			@RequestParam("search") String search, HttpServletRequest request) throws Exception {
		return new ResponseEntity<>(boardService.findByBoard(pageable, search), HttpStatus.OK);
	}

	@DeleteMapping("delete")
	public ResponseEntity<?> deleteBoard(BoardEntity boardEntity) throws Exception {
		boardService.deleteBoard(boardEntity);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PatchMapping("update")
	public ResponseEntity<?> updateBoard(BoardEntity boardEntity) throws Exception {
		boardService.updateBoard(boardEntity);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PatchMapping("hide")
	public ResponseEntity<?> hideBoard(BoardEntity boardEntity) throws Exception {
		boardService.hideBoard(boardEntity);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PatchMapping("toppick")
	public ResponseEntity<?> toppickBoard(BoardEntity boardEntity) throws Exception {
		boardService.topPickBoard(boardEntity);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
