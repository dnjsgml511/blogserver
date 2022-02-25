package com.portfolio.blog.data.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.blog.data.entitiy.BoardEntity;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.service.BoardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "board", description = "DATA CRUD API")

@RestController
@RequestMapping("/board")
@Configuration
public class BoardController {

	public BoardController(@RequestBody BoardService boardService) {
		this.boardService = boardService;
	}

	BoardService boardService;

	@PostMapping("insert")
	@Tag(name = "board")
	@Operation(summary = "Data Insert", description = "Data Insert", responses = {
	        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
	        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
	        @ApiResponse(responseCode = "403", description = "AUTH OUT"),	
		})
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<?> insertBoard(BoardEntity boardEntity) throws Exception {
		boardService.saveBoard(boardEntity);
		return new ResponseEntity<>("저장되었습니다", HttpStatus.OK);
	}

	@GetMapping("topsearch")
	@Tag(name = "board")
	@Operation(summary = "Data Top Search", description = "Data Top Search", responses = {
	        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserEntity.class))),
	        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
	        @ApiResponse(responseCode = "403", description = "AUTH OUT"),	
		})
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<?> topSearchBoard(@PageableDefault(size = 3, page = 0) Pageable pageable) throws Exception {
		return new ResponseEntity<>(boardService.findByTopBoard(pageable), HttpStatus.OK);
	}

	@GetMapping("search")
	@Tag(name = "board")
	@Operation(summary = "Data Search", description = "Data Paging Search", responses = {
	        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserEntity.class))),
	        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
	        @ApiResponse(responseCode = "403", description = "AUTH OUT"),	
		})
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<?> searchBoard(@PageableDefault(size = 15) Pageable pageable,
			@RequestParam("search") String search, HttpServletRequest request) throws Exception {
		return new ResponseEntity<>(boardService.findByBoard(pageable, search), HttpStatus.OK);
	}

	@DeleteMapping("delete")
	@Tag(name = "board")
	@Operation(summary = "Data Delete", description = "Data Delete", responses = {
	        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserEntity.class))),
	        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
	        @ApiResponse(responseCode = "403", description = "AUTH OUT"),	
		})
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<?> deleteBoard(BoardEntity boardEntity) throws Exception {
		boardService.deleteBoard(boardEntity);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PatchMapping("update")
	@Tag(name = "board")
	@Operation(summary = "Data Update", description = "Data Update", responses = {
	        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserEntity.class))),
	        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
	        @ApiResponse(responseCode = "403", description = "AUTH OUT"),	
		})
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<?> updateBoard(BoardEntity boardEntity) throws Exception {
		boardService.updateBoard(boardEntity);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PatchMapping("hide")
	@Tag(name = "board")
	@Operation(summary = "Data Hide", description = "Data Hide", responses = {
	        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserEntity.class))),
	        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
	        @ApiResponse(responseCode = "403", description = "AUTH OUT"),	
		})
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<?> hideBoard(BoardEntity boardEntity) throws Exception {
		boardService.hideBoard(boardEntity);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PatchMapping("toppick")
	@Tag(name = "board")
	@Operation(summary = "Data Top Pick", description = "Data Top Pick", responses = {
	        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserEntity.class))),
	        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
	        @ApiResponse(responseCode = "403", description = "AUTH OUT"),	
		})
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
	public ResponseEntity<?> toppickBoard(BoardEntity boardEntity) throws Exception {
		boardService.topPickBoard(boardEntity);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
