package com.ank.japi.validation;

import java.util.Objects;

public class ValidationError
        extends RuntimeException {

    private static final String DEFAULT_GUIDE = "Retry with other value.";

    private final int    code;
    private final String reason;
    private final String guide;

    protected ValidationError(int code, String reason, String guide) {
        super( reason );
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

    protected ValidationError(int code, String reason) {
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
        if ( !(o instanceof ValidationError error) ) {
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
    public String toString() {
        return "{ code: " + code + ", " +
                "reason: \"" + reason + "\", " +
                "guide: \"" + guide + "\" }";
    }

    public abstract static class Builder<E extends ValidationError> {

        private int    code;
        private String reason;
        private String guide;

        public Builder(
                int defaultCode,
                String defaultReason
        ) {
            this( defaultCode, defaultReason, ValidationError.DEFAULT_GUIDE );
        }

        public Builder(
                int defaultCode,
                String defaultReason,
                String defaultGuide
        ) {
            this.code = defaultCode;
            this.reason = Objects.requireNonNull( defaultReason );
            this.guide = Objects.requireNonNull( defaultGuide );
        }

        protected abstract E newInstance(int code, String reason, String guide);

        public final E build() {
            return newInstance( code, reason, guide );
        }

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
    }
}
