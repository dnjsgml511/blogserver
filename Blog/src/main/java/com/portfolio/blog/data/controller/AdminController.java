package com.portfolio.blog.data.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.blog.data.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@GetMapping("/userlist")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> userlist(HttpServletRequest request) throws Exception{
		
		System.out.println("userlist");
		
		return new ResponseEntity<>(adminService.userlist(), HttpStatus.OK);
	}
	
}
