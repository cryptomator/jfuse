package org.cryptomator.jfuse.api;

import java.nio.file.Path;

/**
 * Thrown when {@link Fuse#mount(String, Path, String...) mounting} failed.
 */
public class FuseMountFailedException extends Exception {

	/**
	 * Constructs a new exception with the specified detail message.
	 *
	 * @param message the detail message
	 */
	public FuseMountFailedException(String message) {
		super(message);
	}

}
