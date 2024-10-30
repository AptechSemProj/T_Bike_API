package se.pj.tbike.api.util;

public interface ResponseObject<T, H extends Handleable> {

	/**
	 * @param h An instance of class implements {@link Handleable} interface.
	 * @return An instance of response object.
	 */
	T fromHandleableObject(H h);

}
