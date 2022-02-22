package com.portfolio.blog.streaming.service;

import java.io.IOException;
import java.util.Optional;

import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.portfolio.blog.util.Paths;

@Service
public class StreamingServiceImpl extends Paths implements StreamingService{

	@Override
	public ResponseEntity<ResourceRegion> streamingVideos(HttpHeaders headers, String name) throws IOException {
		
		UrlResource video = new UrlResource("file:" + SERVER_PATH + name);
		ResourceRegion resourceRegion;
		
		final long chunkSize = 1000000L;
		long contentLength = video.contentLength();

		Optional<HttpRange> optional = headers.getRange().stream().findFirst();
		HttpRange httpRange;

		if (optional.isPresent()) {
			httpRange = optional.get();
			long start = httpRange.getRangeStart(contentLength);
			long end = httpRange.getRangeEnd(contentLength);
			long rangeLength = Long.min(chunkSize, end - start + 1);

			resourceRegion = new ResourceRegion(video, start, rangeLength);

		} else {
			long rangeLength = Long.min(chunkSize, contentLength);
			resourceRegion = new ResourceRegion(video, 0, rangeLength);
		}

		return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
				.contentType(MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
				.body(resourceRegion);

	}

}
