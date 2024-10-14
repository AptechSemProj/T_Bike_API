package se.pj.tbike.util.result;

import java.util.Optional;
import java.util.function.Function;

public interface Result<T> extends ResultList<T> {

	Optional<T> toOptional();

	boolean isEmpty();

	T get();

	<R> Result<R> map( Function<T, R> mapper );

}
