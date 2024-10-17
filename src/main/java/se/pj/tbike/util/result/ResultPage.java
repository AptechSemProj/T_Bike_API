package se.pj.tbike.util.result;

import java.util.function.Function;

public interface ResultPage<T> extends ResultList<T> {

	Integer next();

	Integer previous();

	int getNumber();

	int getSize();

	int getTotalPages();

	long getTotalElements();

	<R> ResultPage<R> map( Function<T, R> mapper );

}
