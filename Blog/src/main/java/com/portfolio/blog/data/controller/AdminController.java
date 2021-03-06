package com.portfolio.blog.data.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.blog.data.dto.auth.UserControllResponse;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "admin", description = "ADMIN API")
@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminservice;
	
	@PostMapping(value = "/controll", produces = "application/json; charset=utf8")
	@Tag(name = "auth")
	@Operation(summary = "User Active", description = "User Active", responses = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "403", description = "FORBIDDEN", content = @Content(schema = @Schema(implementation = String.class))),
	})
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	public ResponseEntity<?> useractive(@RequestBody UserControllResponse response, HttpServletRequest request) throws Exception {
		return new ResponseEntity<>(adminservice.userControll(response, request), HttpStatus.OK);
	}
	
	@GetMapping(value = "/userlist", produces = "application/json; charset=utf8")
	@Tag(name = "admin")
	@Operation(summary = "User List", description = "User List Data")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@ApiResponses(
		@ApiResponse(responseCode = "200", description = "Get User list", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserEntity.class))))
	)
	public ResponseEntity<?> userlist(HttpServletRequest request, UserEntity userEntity) throws Exception {
		return null;
	}
	
	@PostMapping(value = "/userselect", produces = "application/json; charset=utf8")
	@Tag(name = "admin")
	@Operation(summary = "Change User", description = "Change User")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	@ApiResponses(
		@ApiResponse(responseCode = "200", description = "Change User", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserEntity.class))))
	)
	public ResponseEntity<?> userselect(HttpServletRequest request, @RequestBody UserEntity userEntity) throws Exception {
		return null;
	}
}
