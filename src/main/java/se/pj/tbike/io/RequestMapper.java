package se.pj.tbike.io;

/**
 * @param <T>  type of model class
 * @param <RQ> type of request class
 */
@Deprecated
public interface RequestMapper<T, RQ extends RequestType> {

	T map(RQ req);

}
