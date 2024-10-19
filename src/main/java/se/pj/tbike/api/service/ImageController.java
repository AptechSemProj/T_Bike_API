package se.pj.tbike.api.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.boot.context.properties.EnableConfigurationProperties;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;

import org.springframework.util.MimeType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.AllArgsConstructor;

import static se.pj.tbike.api.common.Urls.API_PREFIX;
import static se.pj.tbike.api.common.Urls.IMAGE_API;
import static se.pj.tbike.api.common.Urls.URL_DOWNLOAD;
import static se.pj.tbike.api.common.Urls.URL_UPLOAD;

import se.pj.tbike.api.io.Response;
import se.pj.tbike.api.io.Val;
import se.pj.tbike.service.file.FileStorage;

import java.util.Optional;

@AllArgsConstructor
@EnableConfigurationProperties( { FileStorage.class } )
@RestController
@RequestMapping( path = { API_PREFIX + IMAGE_API } )
public class ImageController {

	private static final String IMAGE_DIRECTORY = "images";

	private FileStorage fileStorage;

	private String createDownloadUrl( String filename,
	                                  HttpServletRequest request ) {
		String baseUrl = ServletUriComponentsBuilder
				.fromContextPath( request )
				.replacePath( null )
				.build()
				.toUriString();
		return baseUrl +
				API_PREFIX + IMAGE_API + URL_DOWNLOAD +
				"/" + filename;
	}

	@PostMapping( URL_UPLOAD )
	public Response<Val<String>> upload(
			@RequestParam( "file" ) MultipartFile file,
			HttpServletRequest request ) {
		try {
			String saved = fileStorage.save( IMAGE_DIRECTORY, file );
			return Response.ok(
					Val.of( createDownloadUrl( saved, request ) )
			);
		} catch ( IOException e ) {
			return Response.internalServerError();
		}
	}

	@GetMapping( URL_DOWNLOAD + "/{filename:.+}" )
	public ResponseEntity<Resource> download(
			@PathVariable( "filename" ) String filename ) throws IOException {
		Path path = fileStorage.download( IMAGE_DIRECTORY, filename );
		if ( path != null ) {
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
		}
		return ResponseEntity.notFound().build();
	}
}
