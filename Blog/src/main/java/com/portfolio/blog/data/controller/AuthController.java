package com.portfolio.blog.data.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.blog.data.dto.JwtRequest;
import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "auth", description = "USER AUTH API")

@RestController
public class AuthController {
	
	@Autowired
	private AuthService service;

    @Operation(summary = "test hello", description = "hello api example")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!")
    })
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest,
			HttpSession session) throws Exception {
		return service.createAuthenticationToken(authenticationRequest);
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody UserEntity userEntity) throws Exception {
		return service.signup(userEntity);
	}

}
