package com.portfolio.blog.data.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.blog.data.dto.auth.SigninResponse;
import com.portfolio.blog.data.dto.auth.SignupResponse;
import com.portfolio.blog.data.dto.auth.UserControllResponse;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "auth", description = "USER AUTH API")

@RestController
@CrossOrigin("*")
public class AuthController {
	
	@Autowired
	private AuthService authService;

	@PostMapping(value = "/authenticate", produces = "application/json; charset=utf8")
	@Tag(name = "auth")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserEntity.class))),
		@ApiResponse(responseCode = "403", description = "FORBIDDEN", content = @Content(schema = @Schema(implementation = String.class))),
	})
	public ResponseEntity<?> createAuthenticationToken(@RequestBody SigninResponse response, HttpServletRequest request) throws Exception {
		return new ResponseEntity<>(authService.signin(response, request), HttpStatus.OK);
	}

	@PostMapping(value = "/signup", produces = "application/json; charset=utf8")
	@Tag(name = "auth")
    @Operation(summary = "Sign Up", description = "User Sign Up", responses = {
    	@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
    	@ApiResponse(responseCode = "403", description = "FORBIDDEN", content = @Content(schema = @Schema(implementation = String.class))),
    })
	public ResponseEntity<?> signup(@RequestBody SignupResponse response) throws Exception {
		return new ResponseEntity<>(authService.signup(response), HttpStatus.OK);
	}
	
	@PostMapping(value = "/user/controll", produces = "application/json; charset=utf8")
	@Tag(name = "auth")
	@Operation(summary = "User Active", description = "User Active", responses = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "403", description = "FORBIDDEN", content = @Content(schema = @Schema(implementation = String.class))),
	})
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
	public ResponseEntity<?> useractive(@RequestBody UserControllResponse response, HttpServletRequest request) throws Exception {
		return new ResponseEntity<>(authService.userControll(response, request.isUserInRole("ROLE_ADMIN")), HttpStatus.OK);
	}
}
