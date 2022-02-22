package com.portfolio.blog.streaming.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.portfolio.blog.streaming.service.StreamingService;

@Controller
public class StreamingController {
	
	@Autowired
	StreamingService streamingService;
	
	@GetMapping(value = "/video/{name}")
	public ResponseEntity<ResourceRegion> streamingVideo(@RequestHeader HttpHeaders headers, @PathVariable String name) throws IOException{
		return streamingService.streamingVideos(headers, name);
	}
	
}
