package org.photoservice.www.api.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import lombok.AllArgsConstructor;
import org.photoservice.www.api.conf.ImageApiUrls;
import org.photoservice.www.data.ImageService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping({ ImageApiUrls.IMAGE_API })
public class ImageController {

	private static final String IMAGE_DIRECTORY = "images";

	private ImageService service;

	private String createUrl(String filename,
	                         HttpServletRequest request) {
		return ServletUriComponentsBuilder
				.fromContextPath( request )
				.replacePath( null )
				.path( ImageApiUrls.IMAGE_API )
				.path( ImageApiUrls.URL_QUERY )
				.build( filename )
				.toString();
	}

	@PostMapping({ ImageApiUrls.URL_LIST_1, ImageApiUrls.URL_LIST_2 })
	public ResponseEntity<Map<String, Object>> upload(@RequestParam MultipartFile file,
	                                                  HttpServletRequest request) {
		try {
			String saved = service.saveFile( IMAGE_DIRECTORY, file );
			String url = createUrl( saved, request );
			return ResponseEntity.ok(
					new HashMap<>() {{
						put( "status", HttpStatus.OK.value() );
						put( "message", HttpStatus.OK.getReasonPhrase() );
						put( "data", url );
					}}
			);
		} catch ( IOException e ) {
			return ResponseEntity.internalServerError()
					.body( new HashMap<>() {{
						put( "status", HttpStatus.INTERNAL_SERVER_ERROR.value() );
						put( "message", e.getMessage() );
					}} );
		} catch ( UnsupportedOperationException e ) {
			return ResponseEntity.badRequest()
					.body( new HashMap<>() {{
						put( "status", HttpStatus.BAD_REQUEST.value() );
						put( "message", e.getMessage() );
					}} );
		}
	}

	@GetMapping({ ImageApiUrls.URL_QUERY })
	public ResponseEntity<Resource> download(@PathVariable String filename) {
		Path path = service.readFile( IMAGE_DIRECTORY, filename );
		if ( path != null ) {
			try {
				String mimeType = Files.probeContentType( path );
				File file = path.toFile();
				Resource resource = new UrlResource( file.toURI() );
				return ResponseEntity.ok()
						.contentType( MediaType.parseMediaType( mimeType ) )
						.contentLength( file.length() )
						.header(
								HttpHeaders.CONTENT_DISPOSITION,
								"attachment; filename=\""
										+ filename
										+ "\""
						).body( resource );
			} catch ( IOException e ) {
				return ResponseEntity.internalServerError().build();
			}
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping({ ImageApiUrls.URL_QUERY })
	public ResponseEntity<Object> delete(@PathVariable String filename) {
		try {
			boolean isDeleted = service.deleteFile( IMAGE_DIRECTORY, filename );
			return isDeleted
					? ResponseEntity.noContent().build()
					: ResponseEntity.notFound().build();
		} catch ( IOException e ) {
			return ResponseEntity.internalServerError().build();
		}
	}
}
