package se.pj.tbike.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import java.util.function.Function;

public class OutputArray<E>
		implements Output.Array<E> {

	private final List<E> value;

	@SafeVarargs
	public OutputArray(E... elms) {
		if ( elms == null ) throw new IllegalArgumentException();
		this.value = List.of( elms );
	}

	public OutputArray(Collection<E> c) {
		if ( c == null ) throw new IllegalArgumentException();
		this.value = new LinkedList<>( c );
	}

	@Override
	public <R> Array<R> map(Function<? super E, ? extends R> mapper) {
		List<R> arr = new ArrayList<>( size() );
		forEach( (e) -> arr.add( mapper.apply( e ) ) );
		return new OutputArray<>( arr );
	}

	@Override
	public E get(int index) {
		return value.get( index );
	}

	@Override
	public int indexOf(E e) {
		return value.indexOf( e );
	}

	@Override
	public Collection<E> get() {
		return value;
	}
}
