package se.pj.tbike.validation;

import java.util.function.Consumer;

public abstract class Requirement
		implements Comparable<Requirement> {

	//************** Abstract Methods ****************//

	public abstract boolean resolve(Object o, Consumer<Object> out);

	//
	/**
	 * the index of requirement
	 */
	private final int identifier;

	/**
	 * the error return if resolve fail.
	 */
	private final Error error;

	public Requirement(int identifier, Error error) {
		this.identifier = identifier;
		this.error = error;
	}

	public final int getIdentifier() {
		return identifier;
	}

	public final Error getError() {
		return error;
	}

	@Override
	public final int compareTo(Requirement o) {
		return identifier - o.identifier;
	}

	@Override
	public final boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof Requirement that) ) {
			return false;
		}
		return identifier == that.identifier;
	}

	@Override
	public final int hashCode() {
		return identifier;
	}
}
