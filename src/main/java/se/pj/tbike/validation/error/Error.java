package se.pj.tbike.validation.error;

import java.util.Objects;

public abstract class Error {

	private static final String MESSAGE_TEMPLATE = "Error[%05d]: %s";
	private static final String DEFAULT_GUIDE = "Retry with other value.";

	private final int code;
	private final String reason;
	private final String guide;

	protected Error(int code, String reason, String guide) {
		if ( reason == null ) {
			throw new IllegalArgumentException();
		}
		if ( guide == null ) {
			throw new IllegalArgumentException();
		}
		this.code = code;
		this.reason = reason;
		this.guide = guide;
	}

	protected Error(int code, String reason) {
		this( code, reason, DEFAULT_GUIDE );
	}

	public final int getCode() {
		return code;
	}

	public final String getReason() {
		return reason;
	}

	public final String getGuide() {
		return guide;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( !(o instanceof Error error) ) {
			return false;
		}
		return code == error.code &&
				Objects.equals( reason, error.reason ) &&
				Objects.equals( guide, error.guide );
	}

	@Override
	public int hashCode() {
		return Objects.hash( code, reason, guide );
	}

	@Override
	public final String toString() {
		return MESSAGE_TEMPLATE.formatted( getCode(), getReason() );
	}

	public abstract static class Builder<E extends Error> {

		private Integer code;
		private String reason;
		private String guide;

		public Builder() {
		}

		public abstract E build();

		public final Builder<E> code(int code) {
			this.code = code;
			return this;
		}

		public final Builder<E> reason(String reason) {
			this.reason = reason;
			return this;
		}

		public final Builder<E> guide(String guide) {
			this.guide = guide;
			return this;
		}

		protected final int getCode(int defaultCode) {
			return code != null ? code : defaultCode;
		}

		protected final String getReason(String defaultReason) {
			return reason != null ? reason : defaultReason;
		}

		protected final String getGuide() {
			return guide != null ? guide : Error.DEFAULT_GUIDE;
		}
	}
}
