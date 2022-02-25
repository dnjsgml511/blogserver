package com.portfolio.blog.data.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
public class AuthController {
	
	@Autowired
	private AuthService service;

	@PostMapping(value = "/authenticate", produces = "application/json; charset=utf8")
	@Tag(name = "auth")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "OK", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserEntity.class))),
		@ApiResponse(responseCode = "403", description = "FORBIDDEN", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = String.class))),
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
	public ResponseEntity<?> signup(@RequestBody UserEntity userEntity, HttpServletResponse response) throws Exception {
		return service.signup(userEntity);
	}

}
