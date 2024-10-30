package se.pj.tbike.util.result;

import static java.util.Collections.unmodifiableList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;

@Deprecated
public class ResultPageImpl<T>
		extends ResultListImpl<T>
		implements ResultPage<T> {

	private static final int PAGE_BASE = 0;

	private final int pageNumber, pageSize, totalPages;
	private final long totalElements;

	public ResultPageImpl(List<T> data, int pageNumber, long totalElements,
	                      int pageSize, int totalPages) {
		super( data );
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
		this.totalPages = totalPages;
		this.totalElements = totalElements;
	}

	public ResultPageImpl(List<T> data, int pageNumber, long totalElements,
	                      int pageSize) {
		this( data, pageNumber, totalElements, pageSize,
				calcPages( totalElements, pageSize ) );
	}

	public ResultPageImpl(Page<T> page) {
		this( page.getContent(), page.getNumber(), page.getTotalElements(),
				page.getSize(), page.getTotalPages() );
	}

	private static int calcPages(long elements, int size) {
		if ( size > elements ) return 1;
		int pages = (int) (elements / size);
		return elements % size > 0 ? pages + 1 : pages;
	}

	@Override
	public Integer next() {
		int next = pageNumber + 1;
		return next < totalPages ? next : null;
	}

	@Override
	public Integer previous() {
		int prev = pageNumber - 1;
		return prev >= PAGE_BASE ? prev : null;
	}

	@Override
	public int getNumber() {
		return pageNumber;
	}

	@Override
	public int getSize() {
		return pageSize;
	}

	@Override
	public int getTotalPages() {
		return totalPages;
	}

	@Override
	public long getTotalElements() {
		return totalElements;
	}

	@Override
	public <R> ResultPage<R> map(Function<T, R> mapper) {
		List<R> list = new ArrayList<>();
		if ( data != null )
			for ( T t : data ) list.add( mapper.apply( t ) );
		return new ResultPageImpl<>( unmodifiableList( list ),
				pageNumber, totalElements, pageSize, totalPages );
	}
}
