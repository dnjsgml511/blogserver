package com.portfolio.blog.data.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	AdminService adminService;

	@Tag(name = "admin")
	@GetMapping("/userlist")
	@Operation(summary = "User List", description = "User List Data")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@ApiResponses(
		@ApiResponse(responseCode = "200", description = "Get User list", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserEntity.class))))
	)
	public ResponseEntity<?> userlist(HttpServletRequest request) throws Exception {
		return new ResponseEntity<>(adminService.userlist(), HttpStatus.OK);
	}

}
