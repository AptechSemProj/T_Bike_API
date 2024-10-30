package se.pj.tbike.core.httpclient;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageHttpClient {

	private static final String IMAGE_API_URL =
			"http://localhost:8080/api/images";

	private static final String URL_QUERY = "/{filename:.+}";

	private static final HttpHeaders HEADERS;

	private static final RestTemplate REST_TEMPLATE;

	static {
		HEADERS = new HttpHeaders();
		HEADERS.setContentType( MediaType.MULTIPART_FORM_DATA );

		REST_TEMPLATE = new RestTemplate();
	}

	public String uploadImage(MultipartFile multipartFile) {
		Resource file = multipartFile.getResource();
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add( "file", file );
		HttpEntity<MultiValueMap<String, Object>> requestEntity =
				new HttpEntity<>( body, HEADERS );
		ResponseEntity<String> response = REST_TEMPLATE
				.postForEntity( IMAGE_API_URL, requestEntity, String.class );
		return response.getBody();
	}

	public void deleteImage(String url) {
		REST_TEMPLATE.delete( url );
	}
}
