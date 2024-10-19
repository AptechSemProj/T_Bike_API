package se.pj.tbike.service.file;

import static java.util.UUID.randomUUID;

import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import java.io.File;
import java.io.IOException;

import java.nio.charset.StandardCharsets;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.apache.commons.io.FilenameUtils.EXTENSION_SEPARATOR;

import org.apache.commons.io.FilenameUtils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.web.multipart.MultipartFile;

import lombok.val;

@ConfigurationProperties( "file-storage" )
public class FileStorage {

	private static final int MAX_NAME_LENGTH = 70;
	private static final String ROOT = "root";

	private final Item root;

	@ConstructorBinding
	public FileStorage( final String root,
	                    final Map<String, String> directories,
	                    final boolean storedInResources )
			throws IOException {
		Item rootItem = createRoot( root, storedInResources );
		if ( directories != null && !directories.isEmpty() ) {
			for ( Entry<String, String> entry : directories.entrySet() ) {
				String name = entry.getKey();
				String path = entry.getValue();
				Path directory = rootItem.add(
						name, Paths.get( path ), new LinkedHashMap<>() );
				if ( Files.notExists( directory ) )
					Files.createDirectories( directory );
			}
		}
		this.root = rootItem;
	}

	public String save( String directory, MultipartFile file )
			throws IOException {
		Item.checkName( directory );
		if ( file == null )
			throw new IllegalArgumentException( "file to save is null" );
		Path dir = this.root.getPath( directory );
		if ( dir == null )
			throw new IllegalArgumentException(
					directory + " is not configured" );
		Path target = createUniquePath( dir, file.getOriginalFilename() );
		String filename = target.getFileName().toString();
		long bytes = Files.copy( file.getInputStream(), target,
				StandardCopyOption.REPLACE_EXISTING );
		if ( bytes >= 0 )
			this.root.add( filename, target, null );
		return filename;
	}

	public Path download( String directory, String filename ) {
		Item.checkName( directory );
		if ( filename == null )
			throw new IllegalArgumentException( "filename is null" );
		Item dir = this.root.findItem( directory );
		if ( dir != null ) {
			Item cache;
			if ( ( cache = dir.findItem( filename ) ) != null )
				return cache.path;
			Path target = resolvePath( dir.path, filename );
			return dir.add( filename, target, null );
		}
		return null;
	}

	private static Item createRoot( String dir,
	                                boolean useResources )
			throws IOException {
		Path root;
		if ( dir != null ) {
			if ( useResources ) {
				String resources = String.join(
						File.separator,
						"src", "main", "resources" );
				Path project = Paths.get( System.getProperty( "user.dir" ) );
				root = resolvePath( project.resolve( resources ), dir );
			} else
				root = resolvePath( null, dir );
		} else
			root = Paths.get( System.getProperty( "user.home" ) );
		return Item.root( ROOT, root );
	}

	private static Path createUniquePath( Path dir,
	                                      String filename ) {
		String gen = getFilename( filename );
		Path path = resolvePath( dir, gen );
		if ( !Files.exists( path ) )
			return path;
		String newName = getFilename( gen );
		return createUniquePath( dir, newName );
	}

	private static Path resolvePath( Path root, String file ) {
		Path path;
		if ( root == null )
			path = Paths.get( file );
		else if ( file != null )
			path = root.resolve( file );
		else
			path = root;
		path = path.normalize();
		return path.isAbsolute() ? path : path.toAbsolutePath();
	}

	private static String getFilename( String original ) {
		String name, extension;
		if ( original != null ) {
			extension = FilenameUtils.getExtension( original );
			name = FilenameUtils.removeExtension( original );
		} else {
			extension = null;
			name = randomUUID().toString();
		}
		Encoder encoder = Base64.getUrlEncoder().withoutPadding();
		byte[] utf8Bytes = name.getBytes( StandardCharsets.UTF_8 );
		String filename = encoder.encodeToString( utf8Bytes );
		if ( filename.length() > MAX_NAME_LENGTH ) {
			filename = filename.substring( 0, MAX_NAME_LENGTH );
		}
		if ( extension != null && !extension.isBlank() ) {
			return filename + EXTENSION_SEPARATOR + extension;
		}
		return filename;
	}

	@Override
	public boolean equals( Object o ) {
		if ( this == o ) return true;
		if ( !( o instanceof FileStorage that ) ) return false;
		return Objects.equals( root, that.root );
	}

	@Override
	public int hashCode() {
		return Objects.hashCode( root );
	}

	@Override
	public String toString() {
		return "FileStorage{ " + root + " }";
	}

	static class Item {

		private String name;
		private Path path;
		private final Map<String, Item> children;

		private Item( String name,
		              Path path,
		              Map<String, Item> children ) {
			checkName( name );
			checkPath( path );
			this.name = name;
			this.path = path;
			this.children = children;
		}

		public static Item root( String name, Path path )
				throws IOException {
			checkName( name );
			checkPath( path );
			if ( Files.notExists( path ) )
				Files.createDirectories( path );
			return new Item( name, path, new LinkedHashMap<>() );
		}

		public Path add( String name, Path path,
		                 Map<String, Item> children ) {
			checkName( name );
			checkPath( path );
			Path resolved = this.path.resolve( path );
			if ( changeChildPath( name, resolved )
					|| changeChildName( resolved, name ) )
				return resolved;
			Item child = new Item( name, resolved, children );
			this.children.put( name, child );
			return child.path;
		}

		public Path getPath( String name ) {
			Item item = findItem( name );
			return item != null ? item.path : null;
		}

		public boolean changeChildName( Path path, String name ) {
			Item item = findItem( path );
			if ( item != null ) item.name = name;
			return item != null;
		}

		public boolean changeChildPath( String name, Path path ) {
			Item item = findItem( name );
			if ( item != null ) item.path = path;
			return item != null;
		}

		public Item findItem( String name ) {
			checkName( name );
			if ( this.name.equals( name ) ) return this;
			Item r;
			if ( ( r = this.children.get( name ) ) != null )
				return r;
			for ( val item : this.children.values() ) {
				if ( item.name.equals( name ) ) return item;
				val list = item.children;
				if ( list != null && !list.isEmpty() )
					return item.findItem( name );
			}
			return null;
		}

		public Item findItem( Path path ) {
			checkPath( path );
			if ( this.path.equals( path ) ) return this;
			for ( val item : this.children.values() ) {
				if ( item.path.equals( path ) ) return item;
				val list = item.children;
				if ( list != null && !list.isEmpty() )
					return item.findItem( path );
			}
			return null;
		}

		@Override
		public boolean equals( Object obj ) {
			if ( obj == this ) return true;
			if ( obj == null || obj.getClass() != this.getClass() )
				return false;
			var that = (Item) obj;
			return Objects.equals( this.name, that.name ) &&
					Objects.equals( this.path, that.path ) &&
					Objects.equals( this.children, that.children );
		}

		@Override
		public int hashCode() {
			return Objects.hash( name, path, children );
		}

		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append( "[ \"%s\" - %s ]: { ".formatted(
					name, children != null
							? "directory"
							: "file"
			) );
			sb.append( "path: \"%s\"".formatted( path ) );
			if ( children != null ) {
				sb.append( ", children: " )
						.append( children.values() );
			}
			sb.append( " }" );
			return sb.toString();
		}

		private static void checkName( String name ) {
			if ( name == null )
				throw new NullPointerException( "name is null" );
			if ( name.isBlank() )
				throw new IllegalArgumentException( "name is blank" );
		}

		private static void checkPath( Path path ) {
			if ( path == null )
				throw new NullPointerException( "path is null" );
		}
	}
}
