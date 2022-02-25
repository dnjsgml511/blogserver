package com.portfolio.blog.data.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.portfolio.blog.data.entitiy.UserEntity;
import com.portfolio.blog.data.service.StreamingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Stream", description = "STREAMING API")

@Controller
public class StreamingController {
	
	@Autowired
	StreamingService streamingService;
	
	@GetMapping(value = "/video/{name}")
	@Tag(name = "Stream")
	@Operation(summary = "Streaming", description = "Streaming Data", responses = {
	        @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = UserEntity.class))),
	        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
	        @ApiResponse(responseCode = "403", description = "AUTH OUT"),	
		})
	public ResponseEntity<ResourceRegion> streamingVideo(@RequestHeader HttpHeaders headers, @PathVariable String name) throws IOException{
		return streamingService.streamingVideos(headers, name);
	}
	
}
