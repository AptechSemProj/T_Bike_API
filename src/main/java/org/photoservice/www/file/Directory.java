package org.photoservice.www.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

final class Directory {

	private final String name;
	private final Path path;
	private final Directory parent;
	private final Map<String, Directory> children;

	private Directory(String name, Path path, Directory parent) {
		this.name = name;
		this.path = path;
		this.parent = parent;
		this.children = new LinkedHashMap<>();
	}

	private static void mkDirs(Path path) {
		if ( Files.notExists( path ) ) {
			try {
				Files.createDirectories( path );
			} catch ( IOException e ) {
				throw new RuntimeException( e );
			}
		}
	}

	private static void checkName(String name) {
		if ( name == null ) {
			throw new NullPointerException( "directory name is null" );
		}
		if ( name.isBlank() ) {
			throw new IllegalArgumentException( "directory name is blank" );
		}
	}

	public static Directory asRootDirectory(Path path) {
		Objects.requireNonNull( path );
		mkDirs( path );
		return new Directory( null, path, null );
	}

	public static Path getDirectoryPath(String name, Directory directory) {
		checkName( name );
		if ( Objects.equals( directory.name, name ) ) {
			return directory.path;
		}
		Directory d = directory.children.get( name );
		if ( d != null ) {
			return d.path;
		}
		if ( directory.parent != null ) {
			return getDirectoryPath( name, directory.parent );
		}
		for ( Directory child : directory.children.values() ) {
			if ( !child.children.isEmpty() ) {
				Path p = getDirectoryPath( name, child );
				if ( p != null ) {
					return p;
				}
			}
		}
		return null;
	}

	public void addDirectory(String name, String path) {
		checkName( name );
		Objects.requireNonNull( path );
		Directory dir = new Directory( name, this.path.resolve( path ), this );
		mkDirs( dir.path );
		this.children.put( dir.name, dir );
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof Directory that) ) {
			return false;
		}
		return hashCode() == that.hashCode();
	}

	@Override
	public int hashCode() {
		AtomicInteger hash = new AtomicInteger( 1 );
		if ( parent != null ) {
			parent.children.forEach( (name, child) -> {
				if ( Objects.equals( this.name, name ) ) {
					return;
				}
				int hashed = Objects.hash(
						child.name,
						child.path,
						child.children
				);
				hash.set( 31 * hash.get() + hashed );
			} );
		}
		return Objects.hash( name, hash.get(), path, children );
	}

	@Override
	public String toString() {
		return "[%s]: { ".formatted( name ) +
				"path: \"%s\"".formatted( path ) +
				", children: " +
				children.values() +
				" }";
	}
}
