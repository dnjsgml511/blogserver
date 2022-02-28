package com.portfolio.blog.data.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.blog.data.dto.JwtRequest;
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
	private AuthService service;

	@PostMapping(value = "/authenticate", produces = "application/json; charset=utf8")
	@Tag(name = "auth")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserEntity.class))),
		@ApiResponse(responseCode = "403", description = "FORBIDDEN", content = @Content(schema = @Schema(implementation = String.class))),
	})
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest,
			HttpSession session) throws Exception {
		return service.createAuthenticationToken(authenticationRequest);
	}

	@PostMapping(value = "/signup", produces = "application/json; charset=utf8")
	@Tag(name = "auth")
    @Operation(summary = "Sign Up", description = "User Sign Up", responses = {
    	@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
    	@ApiResponse(responseCode = "403", description = "FORBIDDEN", content = @Content(schema = @Schema(implementation = String.class))),
    })
	public ResponseEntity<?> signup(@RequestBody UserEntity userEntity) throws Exception {
		return service.signup(userEntity);
	}
	
	@PostMapping(value = "/useractive", produces = "application/json; charset=utf8")
	@Tag(name = "auth")
	@Operation(summary = "User Active", description = "User Active", responses = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "403", description = "FORBIDDEN", content = @Content(schema = @Schema(implementation = String.class))),
	})
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<?> useractive(@RequestBody UserEntity userEntity) throws Exception {
		return service.useractive(userEntity);
	}

	
	@PostMapping(value = "/userblock", produces = "application/json; charset=utf8")
	@Tag(name = "auth")
	@Operation(summary = "User Block", description = "User Block", responses = {
			@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = String.class))),
			@ApiResponse(responseCode = "403", description = "FORBIDDEN", content = @Content(schema = @Schema(implementation = String.class))),
	})
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	public ResponseEntity<?> userblock(@RequestBody UserEntity userEntity) throws Exception {
		return service.userblock(userEntity);
	}

}
