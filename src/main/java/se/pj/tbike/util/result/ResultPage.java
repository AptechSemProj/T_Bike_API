package se.pj.tbike.util.result;

import java.util.function.Function;

import se.pj.tbike.service.QueryService;

public interface ResultPage<T> extends ResultList<T> {

	ResultPage<T> nextPage( QueryService<T, ?> queryService );

	ResultPage<T> previousPage( QueryService<T, ?> queryService );

	Integer next();

	Integer previous();

	int getNumber();

	int getSize();

	int getTotalPages();

	long getTotalElements();

	<R> ResultPage<R> map( Function<T, R> mapper );

}
