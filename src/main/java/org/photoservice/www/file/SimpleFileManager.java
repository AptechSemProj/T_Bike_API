package org.photoservice.www.file;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Predicate;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class SimpleFileManager
		implements FileManager {

	public static final Set<String> SUPPORTED_FILE_EXTENSION = Set.of(
			"jpg", "jpeg", "png", "gif"
	);

	private static final String ROOT = "root";

	private final Item root;

	public SimpleFileManager(FileStorageConfig config) {
		Item rootItem = createRoot( config.root(), config.resourcesBase() );
		final Map<String, String> directories = config.directories();
		if ( directories != null && !directories.isEmpty() ) {
			directories.forEach( rootItem::addDirectory );
		}
		root = rootItem;
	}

	private BufferedImage resize(BufferedImage originalImage, int targetWidth, int targetHeight) {
		BufferedImage resizedImage = new BufferedImage( targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB );
		Graphics2D graphics2D = resizedImage.createGraphics();
		graphics2D.drawImage( originalImage, 0, 0, targetWidth, targetHeight, null );
		graphics2D.dispose();
		return resizedImage;
	}

	@Override
	public final String saveFile(String directory, MultipartFile file)
			throws IOException {
		Item.checkDirName( directory );
		if ( file == null ) {
			throw new IllegalArgumentException( "file is null" );
		}
		Path dir = root.getPath( directory );
		if ( dir == null ) {
			throw new IllegalArgumentException(
					directory + " is not configured"
			);
		}
		String original = file.getOriginalFilename();
		String extension;
		if ( original == null ) {
			extension = "";
		} else {
			String ex = FilenameUtils.getExtension( original );
			extension = ex.equals( "jpeg" ) ? "jpg" : ex;
		}
		if ( !SUPPORTED_FILE_EXTENSION.contains( extension ) ) {
			throw new UnsupportedOperationException(
					"file with extension '" + extension + "' is not supported"
			);
		}
		AtomicReference<Path> target = new AtomicReference<>();
		String filename = generateFilename(
				original, extension,
				(gen) -> {
					Path path = resolvePath( dir, gen );
					boolean isExisted = Files.exists( path );
					if ( !isExisted ) {
						target.set( path );
					}
					return isExisted;
				}
		);
		BufferedImage image = ImageIO.read( file.getInputStream() );
		int width = image.getWidth();
		int height = image.getHeight();
		BufferedImage resized = resize(
				image,
				width - ((width / 100) * 10),
				height - ((height / 100) * 10)
		);
		if ( ImageIO.write( resized, extension, target.get().toFile() ) ) {
			root.addFile( filename, target.get() );
			return filename;
		}
		throw new IOException( "Could not save file" );
	}

	@Override
	public final Path readFile(String directory, String filename) {
		Item.checkDirName( directory );
		if ( filename == null ) {
			throw new IllegalArgumentException( "filename is null" );
		}
		Item dir = root.findChild( directory );
		if ( dir != null ) {
			Item cache;
			if ( (cache = dir.findChild( filename )) != null ) {
				return cache.path;
			}
			Path target = resolvePath( dir.path, filename );
			if ( Files.exists( target ) ) {
				return dir.addFile( filename, target );
			} else {
				return dir.addNotExistsFile( filename );
			}
		}
		return null;
	}

	@Override
	public boolean deleteFile(String directory, String filename)
			throws IOException {
		Item.checkDirName( directory );
		if ( filename == null ) {
			throw new IllegalArgumentException( "filename is null" );
		}
		Item dir = root.findChild( directory );
		Path target;
		if ( dir != null ) {
			Item cache;
			if ( (cache = dir.findChild( filename )) != null ) {
				target = cache.path;
			} else {
				target = resolvePath( dir.path, filename );
			}
			if ( target == null ) {
				return false;
			}
			boolean deleted;
			if ( (deleted = Files.deleteIfExists( target )) && cache != null ) {
				dir.delete( cache );
			}
			return deleted;
		}
		return false;
	}

	protected abstract String generateFilename(String original,
	                                           String extension,
	                                           Predicate<String> existsTester);

	private static Item createRoot(String dir,
	                               boolean useResources) {
		Path root;
		if ( dir != null ) {
			if ( useResources ) {
				String resources = String.join(
						File.separator,
						"src", "main", "resources"
				);
				Path project = Paths.get( System.getProperty( "user.dir" ) );
				root = resolvePath( project.resolve( resources ), dir );
			} else {
				root = resolvePath( null, dir );
			}
		} else {
			root = Paths.get( System.getProperty( "user.home" ) );
		}
		return Item.asRootDirectory( ROOT, root );
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
		if ( !(o instanceof SimpleFileManager that) ) {
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
		return "SimpleFileManager{ " + root + " }";
	}

	static class Item {

		private String name;
		private Path path;
		private final Item parent;
		private final Map<String, Item> children;

		private Item(String name, Path path,
		             Item parent, Map<String, Item> children) {
			checkDirName( name );
//			checkPath( path );
			this.name = name;
			this.path = path;
			this.parent = parent;
			this.children = children;
		}

		private static void mkDirs(Path path) {
			checkPath( path );
			if ( Files.notExists( path ) ) {
				try {
					Files.createDirectories( path );
				} catch ( IOException e ) {
					throw new RuntimeException( e );
				}
			}
		}

		public static Item asRootDirectory(String name, Path path) {
			checkDirName( name );
			checkPath( path );
			mkDirs( path );
			return new Item( name, path, null, new LinkedHashMap<>() );
		}

		public void delete(Item i) {
			if ( i.parent != null ) {
				parent.children.remove( i.name, i );
			}
		}

		public void addDirectory(String name, String path) {
			Path dir = add( name, Paths.get( path ), new LinkedHashMap<>() );
			mkDirs( dir );
		}

		public Path addFile(String name, Path path) {
			return add( name, path, null );
		}

		public Path addNotExistsFile(String name) {
			Item child = new Item( name, null, this, null );
			this.children.put( child.name, child );
			return child.path;
		}

		public Path add(String name, Path path,
		                Map<String, Item> children) {
			checkDirName( name );
			checkPath( path );
			Path resolved = this.path.resolve( path );
			if ( changePath( name, resolved ) || rename( resolved, name ) ) {
				return resolved;
			}
			Item child = new Item( name, resolved, this, children );
			this.children.put( child.name, child );
			return child.path;
		}

		public Path getPath(String name) {
			Item item = findChild( name );
			return item != null ? item.path : null;
		}

		public boolean rename(Path path, String name) {
			Item item = findChild( path );
			if ( item != null ) {
				item.name = name;
			}
			return item != null;
		}

		public boolean changePath(String name, Path path) {
			Item item = findChild( name );
			if ( item != null ) {
				item.path = path;
			}
			return item != null;
		}

		public Item findChild(String name) {
			checkDirName( name );
			if ( this.name.equals( name ) ) {
				return this;
			}
			Item r;
			if ( (r = this.children.get( name )) != null ) {
				return r;
			}
			for ( final Item item : this.children.values() ) {
				if ( item.name.equals( name ) ) {
					return item;
				}
				if ( item.isDirectory() ) {
					return item.findChild( name );
				}
			}
			return null;
		}

		public Item findChild(Path path) {
			checkPath( path );
			if ( this.path.equals( path ) ) {
				return this;
			}
			for ( final Item item : children.values() ) {
				if ( item.path.equals( path ) ) {
					return item;
				}
				if ( item.isDirectory() ) {
					return item.findChild( path );
				}
			}
			return null;
		}

		public boolean isDirectory() {
			return children != null;
		}

		@Override
		public boolean equals(Object obj) {
			if ( obj == this ) {
				return true;
			}
			if ( !(obj instanceof Item that) ) {
				return false;
			}
			return Objects.equals( name, that.name ) &&
					Objects.equals( path, that.path ) &&
					Objects.equals( children, that.children );
		}

		@Override
		public int hashCode() {
			return Objects.hash( name, path, children );
		}

		@Override
		public String toString() {
			String type = isDirectory() ? "directory" : "file";
			StringBuilder sb = new StringBuilder();
			sb.append( "[ \"%s\" - %s ]: { ".formatted( name, type ) );
			sb.append( "path: \"%s\"".formatted( path ) );
			if ( children != null ) {
				sb.append( ", children: " );
				sb.append( children.values() );
			}
			sb.append( " }" );
			return sb.toString();
		}

		private static void checkDirName(String name) {
			if ( name == null ) {
				throw new NullPointerException( "directory name is null" );
			}
			if ( name.isBlank() ) {
				throw new IllegalArgumentException( "directory name is blank" );
			}
		}

		private static void checkPath(Path path) {
			if ( path == null ) {
				throw new NullPointerException( "path is null" );
			}
		}
	}
}
