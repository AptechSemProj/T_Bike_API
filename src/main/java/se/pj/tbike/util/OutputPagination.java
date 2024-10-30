package se.pj.tbike.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class OutputPagination<E>
		extends OutputArray<E>
		implements Output.Pagination<E> {

	public static final int BASED = 0;

	private final int totalPages, current, size;

	private final long totalElements;

	public OutputPagination(Collection<E> data,
	                        int current, int size,
	                        long elements, int pages) {
		super( data );
		if ( current < 0 ) {
			throw new IllegalArgumentException();
		}
		if ( size < 0 ) {
			throw new IllegalArgumentException();
		}
		if ( elements < 0 ) {
			throw new IllegalArgumentException();
		}
		if ( pages < 0 ) {
			throw new IllegalArgumentException();
		}
		this.current = current;
		this.size = size;
		this.totalElements = elements;
		this.totalPages = pages;
	}

	public OutputPagination(Collection<E> data,
	                        int current, int size,
	                        long elements) {
		this(
				data,
				current, size,
				elements, calcTotalPages( elements, size )
		);
	}

	public OutputPagination(E[] data,
	                        int current, int size,
	                        long elements, int pages) {
		this( List.of( data ), current, size, elements, pages );
	}

	public OutputPagination(E[] data,
	                        int current, int size,
	                        long elements) {
		this(
				List.of( data ),
				current, size,
				elements, calcTotalPages( elements, size )
		);
	}

	private static int calcTotalPages(long elements, int size) {
		if ( size > elements ) return 1;
		int pages = (int) (elements / size);
		return (elements % size) != 0
				? pages + 1
				: pages;
	}

	@Override
	public <R> Pagination<R> map(Function<? super E, ? extends R> mapper) {
		List<R> arr = new ArrayList<>( size() );
		forEach( (e) -> arr.add( mapper.apply( e ) ) );
		return new OutputPagination<>( arr,
				current, size,
				totalElements, totalPages
		);
	}

	@Override
	public Integer next() {
		int next = current + 1;
		return next < totalPages ? next : null;
	}

	@Override
	public Integer previous() {
		int prev = current - 1;
		return prev >= BASED ? prev : null;
	}

	@Override
	public int getNumber() {
		return current;
	}

	@Override
	public int getSize() {
		return size;
	}

	@Override
	public int getTotalPages() {
		return totalPages;
	}

	@Override
	public long getTotalElements() {
		return totalElements;
	}
}
