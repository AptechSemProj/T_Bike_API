package org.photoservice.www.data;

import org.apache.commons.io.FilenameUtils;
import org.photoservice.www.file.StdFileManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

import java.nio.charset.StandardCharsets;

@Service
@EnableConfigurationProperties({ ImageService.Conf.class })
public class ImageService extends StdFileManager {

	@ConfigurationProperties("file-storage")
	public record Conf(String root, boolean resourcesBase,
	                   Map<String, String> directories) {
		public Configuration toConfiguration() {
			Path rootDir;
			if ( resourcesBase ) {
				String resources = String.join(
						File.separator,
						"src", "main", "resources"
				);
				Path project = Paths.get( System.getProperty( "user.dir" ) );
				rootDir = project.resolve( resources ).resolve( root );
			} else {
				rootDir = Paths.get( root );
			}
			return new Configuration( rootDir, directories );
		}
	}

	private static final int MAX_NAME_LENGTH = 70;

	public ImageService(Conf conf) {
		super( conf.toConfiguration() );
	}

	@Override
	protected Set<String> configureExtensions() {
		return Set.of( "jpg", "jpeg", "png", "gif", "webp" );
	}

	private BufferedImage resize(BufferedImage originalImage,
	                             int originalWidth, int originalHeight) {
		int width = originalWidth - ((originalWidth / 100) * 10);
		int height = originalHeight - ((originalHeight / 100) * 10);
		int type = BufferedImage.TYPE_INT_RGB;
		BufferedImage resizedImage = new BufferedImage( width, height, type );
		Graphics2D graphics2D = resizedImage.createGraphics();
		graphics2D.drawImage( originalImage, 0, 0, width, height, null );
		graphics2D.dispose();
		return resizedImage;
	}

	@Override
	protected boolean write(InputStream in, String extension, Path target)
			throws IOException {
		BufferedImage image = ImageIO.read( in );
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage resized = resize( image, width, height );
		return ImageIO.write(
				resized,
				extension.equals( "jpeg" ) ? "jpg" : extension,
				target.toFile()
		);
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
