package se.pj.tbike.validation;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Validators {

	private Map<String, KeyValue> bindings;

	public KeyValue bind(String key, Object value) {
//		return bindings.computeIfAbsent( key, k -> new KeyValue( value ) );
		return null;
	}

	public Validators requireNotNull(KeyValue... pairs) {
		return this;
	}

//	public

	public static class KeyValue {

		private final Object value;
		private final Set<Requirement> requirements;

		private KeyValue(Object value) {
			this.value = value;
			this.requirements = new LinkedHashSet<>();
		}

		public KeyValue require(Requirement... requirements) {
			return this;
		}

	}

}
