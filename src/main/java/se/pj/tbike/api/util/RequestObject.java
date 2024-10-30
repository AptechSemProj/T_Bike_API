package se.pj.tbike.api.util;

public interface RequestObject<H extends Handleable> {

	/**
	 * This method has function as a mapper, that will pass data of this
	 * {@link RequestObject} class's instance to an instance of
	 * {@link Handleable} class.
	 *
	 * @return The instance of {@code Handleable} class.
	 */
	H toHandleableObject();

}
