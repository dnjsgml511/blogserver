package com.portfolio.blog.streaming.service;

import java.io.IOException;

import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public interface StreamingService {
	public ResponseEntity<ResourceRegion> streamingVideos(HttpHeaders headers, String name) throws IOException;
}
