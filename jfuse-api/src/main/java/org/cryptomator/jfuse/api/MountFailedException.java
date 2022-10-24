package org.cryptomator.jfuse.api;

import java.nio.file.Path;

/**
 * Thrown when {@link Fuse#mount(String, Path, String...) mounting} failed.
 */
public class MountFailedException extends Exception {

	/**
	 * Constructs a new exception with the specified detail message.
	 *
	 * @param message the detail message
	 */
	public MountFailedException(String message) {
		super(message);
	}

}
