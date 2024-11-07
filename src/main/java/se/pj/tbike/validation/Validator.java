package se.pj.tbike.validation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Validator {

	private final Map<Requirement, Error> requirements;

	public Validator() {
		this.requirements = new HashMap<>();
	}

	public int accept(Requirement... requirements) {
		if ( requirements == null || requirements.length == 0 ) {
			return 0;
		}
		for ( Requirement r : requirements ) {
			if ( r != null ) {
				this.requirements.put( r, r.getError() );
			}
		}
		return this.requirements.size();
	}

	public Set<Requirement> getRequirements() {
		return Set.copyOf( requirements.keySet() );
	}

	public ValidationResult validate(final Object value) {
		var val = new AtomicReference<>( value );
		for ( Requirement r : getRequirements() ) {
			if ( !r.resolve( val.get(), val::set ) ) {
				return ValidationResult.failure( r.getError() );
			}
		}
		return ValidationResult.success( val.get() );
	}

	public Set<Error> getReturnableErrors() {
		return Set.copyOf( requirements.values() );
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof Validator that) ) {
			return false;
		}
		return Objects.equals( requirements, that.requirements );
	}

	@Override
	public int hashCode() {
		return Objects.hashCode( requirements );
	}
}
