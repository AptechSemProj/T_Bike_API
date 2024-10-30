package se.pj.tbike.io;

/**
 * @param <T>  type of model class
 * @param <RP> type of response class
 */
public interface ResponseMapper<T, RP extends ResponseType> {

	RP map(T t);

}