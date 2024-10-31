package se.pj.tbike.api.util;

import se.pj.tbike.util.json.DynamicJson;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public abstract class ResponseTemplate
		implements DynamicJson {

	private final Map<String, Replaceable> template;
	private final String bodyKey;

	private final Collector<Replaceable, ?, Map<String, Object>> collector =
			Collectors.toMap( Replaceable::name, Replaceable::value );

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

	public final ResponseTemplate set(String fieldName, Object value) {
		if ( template != null ) {
			Replaceable replaceable =
					template.computeIfAbsent( fieldName, Replaceable::new );
			replaceable.value = value;
		}
		return this;
	}

	@Override
	public final Map<String, Object> toJson() {
		if ( !isConfigured() ) {
			return null;
		}
		return template.values()
				.parallelStream()
				.filter( Replaceable::isValid )
				.collect( collector );
	}

	public static final class Replaceable {
		private final String name;
		private Object value;

		public Replaceable(String name) {
			this.name = name;
		}

		private boolean isValid() {
			return name != null && value != null;
		}

		private String name() {
			return name;
		}

		private Object value() {
			return value;
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
					Objects.equals( value, that.value );
		}

		@Override
		public int hashCode() {
			return Objects.hash( name, value );
		}
	}
}
