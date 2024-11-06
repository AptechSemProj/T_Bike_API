package org.photoservice.www.data;

import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.photoservice.www.file.FileStorageConfig;
import org.photoservice.www.file.SimpleFileManager;

import java.util.Base64;
import java.util.UUID;
import java.util.function.Predicate;

import java.nio.charset.StandardCharsets;

@Service
@EnableConfigurationProperties({ FileStorageConfig.class })
public class ImageService extends SimpleFileManager {

	private static final int MAX_NAME_LENGTH = 70;

	public ImageService(FileStorageConfig config) {
		super( config );
	}

	@Override
	protected String generateFilename(final String original,
	                                  final String extension,
	                                  Predicate<String> existsTester) {
		String name;
		if ( original != null ) {
			name = FilenameUtils.removeExtension( original );
		} else {
			name = String.valueOf( UUID.randomUUID() );
		}
		Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
		byte[] utf8Bytes = name.getBytes( StandardCharsets.UTF_8 );
		String encoded = encoder.encodeToString( utf8Bytes );
		StringBuilder filename = getFilename( encoded, extension );
		String result = filename.toString();
		return existsTester.test( result )
				? generateFilename( result, extension, existsTester )
				: result;
	}

	private static StringBuilder getFilename(String encoded,
	                                         String extension) {
		StringBuilder filename = new StringBuilder( encoded );
		int nameLength = filename.length();
		if ( nameLength > MAX_NAME_LENGTH ) {
			filename.delete( MAX_NAME_LENGTH, nameLength );
		}
		if ( extension != null && !extension.isBlank() ) {
			filename.append( FilenameUtils.EXTENSION_SEPARATOR );
			filename.append( extension );
		}
		return filename;
	}
}
