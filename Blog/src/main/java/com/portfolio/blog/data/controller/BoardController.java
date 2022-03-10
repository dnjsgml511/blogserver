package com.portfolio.blog.data.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.blog.data.dto.board.InsertBoardResponse;
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
@CrossOrigin("*")
public class BoardController {

	@Autowired
	BoardService boardService;

	/**
	 * 데이터 저장
	 * @param boardEntity
	 * @return ResponseEntity<?>
	 * @throws Exception
	 */
	@PostMapping(value = "/insert", produces = "application/json; charset=utf8")
	@Tag(name = "board")
	@Operation(summary = "Data Insert", description = "Data Insert", responses = {
	        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
	        @ApiResponse(responseCode = "403", description = "AUTH OUT", content = @Content(schema = @Schema(implementation = String.class)))
		})
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER')")
	public ResponseEntity<?> insertBoard(@RequestBody InsertBoardResponse response, HttpServletRequest request) throws Exception {
		return new ResponseEntity<>(boardService.insertBoard(response, request), HttpStatus.OK);
	}

	/**
	 * 상단 고정 데이터 검색
	 * @param pageable
	 * @return ResponseEntity<?>
	 * @throws Exception
	 */
	@GetMapping(value = "/topsearch", produces = "application/json; charset=utf8")
	@Tag(name = "board")
	@Operation(summary = "Data Top Search", description = "Data Top Search", responses = {
	        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserEntity.class))),
	        @ApiResponse(responseCode = "403", description = "AUTH OUT", content = @Content(schema = @Schema(implementation = String.class)))
		})
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER')")
	public ResponseEntity<?> topSearchBoard(@PageableDefault(size = 3, page = 0) Pageable pageable) throws Exception {
		return null;
	}

	/**
	 * 데이터 페이징 검색
	 * @param pageable
	 * @param search
	 * @param request
	 * @return ResponseEntity<?>
	 * @throws Exception
	 */
	@GetMapping(value = "/search/{user}", produces = "application/json; charset=utf8")
	@Tag(name = "board")
	@Operation(summary = "Data Search", description = "Data Paging Search", responses = {
	        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserEntity.class))),
	        @ApiResponse(responseCode = "403", description = "AUTH OUT", content = @Content(schema = @Schema(implementation = String.class)))
		})
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER')")
	public ResponseEntity<?> searchBoard(@PageableDefault(size = 15) Pageable pageable,
			@RequestParam(name = "search", defaultValue = "" ) String search, @PathVariable(name = "user") String user, HttpServletRequest request) throws Exception {
		return null;
	}

	/**
	 * 데이터 삭제
	 * @param boardEntity
	 * @return
	 * @throws Exception
	 */
	@DeleteMapping(value = "/delete", produces = "application/json; charset=utf8")
	@Tag(name = "board")
	@Operation(summary = "Data Delete", description = "Data Delete", responses = {
	        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserEntity.class))),
	        @ApiResponse(responseCode = "403", description = "AUTH OUT", content = @Content(schema = @Schema(implementation = String.class)))
		})
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	public ResponseEntity<?> deleteBoard(@RequestParam int num, HttpServletRequest request) throws Exception {
		return new ResponseEntity<>(boardService.deleteBoard(num, request), HttpStatus.OK);
	}

	/**
	 * 데이터 수정
	 * @param boardEntity
	 * @return ResponseEntity<?>
	 * @throws Exception
	 */
	@PatchMapping(value = "/update", produces = "application/json; charset=utf8")
	@Tag(name = "board")
	@Operation(summary = "Data Update", description = "Data Update", responses = {
	        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserEntity.class))),
	        @ApiResponse(responseCode = "403", description = "AUTH OUT", content = @Content(schema = @Schema(implementation = String.class)))
		})
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER')")
	public ResponseEntity<?> updateBoard(@RequestBody BoardEntity boardEntity) throws Exception {
		return null;
	}

	/**
	 * 데이터 숨기기
	 * @param boardEntity
	 * @return ResponseEntity<?>
	 * @throws Exception
	 */
	@PatchMapping(value = "/hide", produces = "application/json; charset=utf8")
	@Tag(name = "board")
	@Operation(summary = "Data Hide", description = "Data Hide", responses = {
	        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserEntity.class))),
	        @ApiResponse(responseCode = "403", description = "AUTH OUT", content = @Content(schema = @Schema(implementation = String.class)))
		})
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER')")
	public ResponseEntity<?> hideBoard(BoardEntity boardEntity) throws Exception {
		return null;
	}

	/**
	 * 데이터 상단 고정
	 * @param boardEntity
	 * @return ResponseEntity<?>
	 * @throws Exception
	 */
	@PatchMapping(value = "/toppick", produces = "application/json; charset=utf8")
	@Tag(name = "board")
	@Operation(summary = "Data Top Pick", description = "Data Top Pick", responses = {
	        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserEntity.class))),
	        @ApiResponse(responseCode = "403", description = "AUTH OUT", content = @Content(schema = @Schema(implementation = String.class)))
		})
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	public ResponseEntity<?> toppickBoard(BoardEntity boardEntity) throws Exception {
		return null;
	}
}
