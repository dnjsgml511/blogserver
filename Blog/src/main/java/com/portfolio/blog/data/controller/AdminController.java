package com.portfolio.blog.data.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<?> userlist() throws Exception{
		return new ResponseEntity<>(adminService.userlist(), HttpStatus.OK);
	}
	
}
