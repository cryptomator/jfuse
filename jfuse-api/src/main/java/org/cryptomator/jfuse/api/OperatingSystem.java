package org.cryptomator.jfuse.api;

/**
 * Operating System
 */
public enum OperatingSystem {
	/**
	 * Linux
	 */
	LINUX,

	/**
	 * macOS
	 */
	MAC,

	/**
	 * Windows
	 */
	WINDOWS,

	/**
	 * Other or unknown operating system
	 */
	UNKNOWN;

	private static final String OS_NAME = System.getProperty("os.name").toLowerCase();

	/**
	 * The currently running operating system.
	 */
	public static final OperatingSystem CURRENT = getCurrent();

	private static OperatingSystem getCurrent() {
		if (OS_NAME.contains("linux")) {
			return LINUX;
		} else if (OS_NAME.contains("mac")) {
			return MAC;
		} else if (OS_NAME.contains("windows")) {
			return WINDOWS;
		} else {
			return UNKNOWN;
		}
	}
}

