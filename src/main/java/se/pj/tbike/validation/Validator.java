package se.pj.tbike.validation;

import lombok.Getter;

public interface Validator {

	ValidationResult validate(Object value);

	default void applyConf(Configuration conf) {
	}

	boolean equals(Object o);

	int hashCode();

	@Getter
	final class Configuration {

		private boolean acceptLogging = false;

		public Configuration logging(boolean accept) {
			this.acceptLogging = accept;
			return this;
		}
	}
}
