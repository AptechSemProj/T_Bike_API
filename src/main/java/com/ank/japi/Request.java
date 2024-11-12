package com.ank.japi;

public interface Request<H extends Handleable> {

    /**
     * This method has function as a mapper, that will pass data of this
     * {@link Request} class's instance to an instance of {@link Handleable}
     * class.
     *
     * @return The instance of {@code Handleable} class.
     */
    H toHandleableObject();

}
