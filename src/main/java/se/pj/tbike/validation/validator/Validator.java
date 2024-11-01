package se.pj.tbike.validation.validator;

import lombok.Getter;
import se.pj.tbike.validation.ValidationResult;
import se.pj.tbike.validation.error.Error;

import java.util.function.Consumer;

public interface Validator {

	ValidationResult validate(Object value);

	default void configure(Consumer<Configuration> conf) {
	}

	default Error[] getReturnableErrors() {
		return new Error[0];
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
