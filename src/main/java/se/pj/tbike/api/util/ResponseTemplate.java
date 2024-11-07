package se.pj.tbike.api.util;

import se.pj.tbike.util.json.DynamicJson;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public abstract class ResponseTemplate
		implements DynamicJson {

	private final Map<String, Replaceable> template;
	private final String bodyKey;

	protected ResponseTemplate() {
		this.template = configureTemplate();
		this.bodyKey = configureBodyKey();
	}

	protected abstract Map<String, Replaceable> configureTemplate();

	protected String configureBodyKey() {
		return "data";
	}

	public final boolean isConfigured() {
		return template != null;
	}

	public final ResponseTemplate body(Object value) {
		return set( bodyKey, value );
	}

	public final ResponseTemplate set(String path, Object value) {
		if ( path == null || path.isBlank() ) {
			throw new IllegalArgumentException( "path is null or empty" );
		}
		if ( template == null ) {
			return this;
		}
		String[] names = path.split( "\\." );
		int len = names.length;
		Map<String, Replaceable> parent = template;
		for ( int i = 0; i < len; i++ ) {
			Replaceable obj = parent.computeIfAbsent( names[i], Replaceable::new );
			if ( i + 1 < len ) {
				Replaceable child = new Replaceable( names[i + 1] );
				parent = obj.addChild( child );
			} else if ( obj.children.isEmpty() ) {
				obj.value = value;
				return this;
			}
		}
		return this;
	}

	private static Map<String, Object> transform(Map<String, Replaceable> map) {
		return map.values()
				.parallelStream()
				.filter( Replaceable::isValid )
				.collect( Collectors.toMap( Replaceable::name, Replaceable::value ) );
	}

	@Override
	public final Map<String, Object> toJson() {
		return isConfigured() ? transform( template ) : null;
	}

	/**
	 *
	 */
	public static final class Replaceable {
		private final String name;
		private final Map<String, Replaceable> children;
		private Object value;

		public Replaceable(String name, Map<String, Replaceable> children) {
			if ( name == null || name.isBlank() ) {
				throw new IllegalArgumentException( "name is null or empty" );
			}
			if ( children == null ) {
				throw new IllegalArgumentException( "children is null" );
			}
			this.name = name;
			this.children = children;
		}

		public Replaceable(String name) {
			this( name, new HashMap<>() );
		}

		private boolean isValid() {
			return name != null && (value != null || !children.isEmpty());
		}

		private String name() {
			return name;
		}

		private Object value() {
			if ( children.isEmpty() ) {
				return value;
			} else {
				return transform( children );
			}
		}

		private Map<String, Replaceable> addChild(Replaceable child) {
			children.putIfAbsent( child.name(), child );
			return children;
		}

		@Override
		public boolean equals(Object o) {
			if ( this == o ) {
				return true;
			}
			if ( !(o instanceof Replaceable that) ) {
				return false;
			}
			return Objects.equals( name, that.name ) &&
					Objects.equals( children, that.children ) &&
					Objects.equals( value, that.value );
		}

		@Override
		public int hashCode() {
			return Objects.hash( name, children, value );
		}
	}
}
