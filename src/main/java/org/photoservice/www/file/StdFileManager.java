package org.photoservice.www.file;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class StdFileManager
		implements FileManager {

	protected abstract Set<String> configureExtensions();

	protected abstract String generateFilename(String original, String extension,
	                                           Predicate<String> existsTester);

	protected abstract boolean write(InputStream in,
	                                 String extension,
	                                 Path target) throws IOException;

	private final Directory root;
	private final Set<String> extensions;

	public StdFileManager(Configuration config) {
		root = createRoot( config.root() );
		config.directories().forEach( root::addDirectory );
		extensions = configureExtensions();
	}

	private static Directory createRoot(Path dir) {
		Path root = Objects.requireNonNullElseGet( dir,
				() -> Paths.get( System.getProperty( "user.home" ) )
		);
		return Directory.asRootDirectory( root );
	}

	private String checkAndGetExtension(String filename) {
		final String extension;
		if ( filename == null ) {
			extension = "";
		} else {
			extension = FilenameUtils.getExtension( filename );
		}
		if ( extensions.contains( extension ) ) {
			return extension;
		}
		throw new UnsupportedOperationException(
				"File '" + extension + "' is not supported"
		);
	}

	@Override
	public String saveFile(String directory, MultipartFile file)
			throws IOException {
		Objects.requireNonNull( file, "file is null" );
		Path dir = Objects.requireNonNull(
				Directory.getDirectoryPath( directory, root ),
				directory + " is not configured"
		);
		var target = new AtomicReference<Path>( null );
		String original = file.getOriginalFilename();
		String extension = checkAndGetExtension( original );
		String filename = generateFilename( original, extension, gen -> {
			target.set( resolvePath( dir, gen ) );
			return Files.exists( target.get() );
		} );
		if ( write( file.getInputStream(), extension, target.get() ) ) {
			return filename;
		}
		throw new IOException( "Could not save file" );
	}

	@Override
	public Path readFile(String directory, String filename) {
		Objects.requireNonNull( filename, "filename is null" );
		checkAndGetExtension( filename );
		Path dir = Directory.getDirectoryPath( directory, root );
		if ( dir == null ) {
			return null;
		}
		Path target = resolvePath( dir, filename );
		return Files.exists( target ) ? target : null;
	}

	@Override
	public boolean deleteFile(String directory, String filename)
			throws IOException {
		Objects.requireNonNull( filename, "filename is null" );
		checkAndGetExtension( filename );
		Path dir = Directory.getDirectoryPath( directory, root );
		if ( dir == null ) {
			return false;
		}
		Path target = resolvePath( dir, filename );
		return Files.deleteIfExists( target );
	}

	private static Path resolvePath(Path root, String file) {
		Path path;
		if ( root == null ) {
			path = Paths.get( file );
		} else if ( file != null ) {
			path = root.resolve( file );
		} else {
			path = root;
		}
		Path abs = path.isAbsolute() ? path : path.toAbsolutePath();
		return abs.normalize();
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof StdFileManager that) ) {
			return false;
		}
		return Objects.equals( root, that.root );
	}

	@Override
	public int hashCode() {
		return Objects.hashCode( root );
	}

	@Override
	public String toString() {
		return "StdFileManager{ " + root + " }";
	}
}
