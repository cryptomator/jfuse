package org.cryptomator.jfuse.api;

public enum Architecture {
	AMD64,
	ARM64,
	UNKNOWN;

	private static final String OS_ARCH = System.getProperty("os.arch").toLowerCase();
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

