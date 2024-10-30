package se.pj.tbike.util.result;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

@Deprecated
public interface ResultList<E> {

	<R> ResultList<R> map( Function<E, R> mapper );

	List<E> toList();

	ResultList<E> distinct();

	<K> Map<K, E> toMap( Function<E, K> keyProvider );

	Stream<E> stream();

}
