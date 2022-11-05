package org.cryptomator.jfuse.api.platforms;

/**
 * CPU architecture
 */
public enum Architecture {
	/**
	 * AMD64 aka. x86_64
	 */
	AMD64,

	/**
	 * ARM64 aka. aarch64
	 */
	ARM64,

	/**
	 * Other or unknown architecture
	 */
	UNKNOWN;

	private static final String OS_ARCH = System.getProperty("os.arch").toLowerCase();

	/**
	 * The CPU architecture of the current machine.
	 */
	public static final Architecture CURRENT = getCurrent();

	private static Architecture getCurrent() {
		if (OS_ARCH.contains("x86_64") || OS_ARCH.contains("amd64")) {
			return AMD64;
		} else if (OS_ARCH.contains("aarch64")) {
			return ARM64;
		} else {
			return UNKNOWN;
		}
	}
}

