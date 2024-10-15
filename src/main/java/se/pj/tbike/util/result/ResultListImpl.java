package se.pj.tbike.util.result;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ResultListImpl<T>
		implements ResultList<T> {

	protected final List<T> data;

	public ResultListImpl( List<T> data ) {
		this.data = data;
	}

	@Override
	public <R> ResultList<R> map( Function<T, R> mapper ) {
		List<R> list = new ArrayList<>();
		if ( data != null )
			for ( T e : data ) list.add( mapper.apply( e ) );
		return new ResultListImpl<>( unmodifiableList( list ) );
	}

	@Override
	public List<T> toList() {
		return data != null ? data : List.of();
	}

	@Override
	public ResultList<T> distinct() {
		return new ResultListImpl<>( stream().distinct().toList() );
	}

	@Override
	public Stream<T> stream() {
		return data != null ? data.stream() : Stream.empty();
	}

	@Override
	public <K> Map<K, T> toMap( Function<T, K> keyProvider ) {
		return stream().collect( Collectors
				.toMap( keyProvider, Function.identity() ) );
	}
}
