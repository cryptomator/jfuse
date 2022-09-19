package org.cryptomator.jfuse.api;

public enum OperatingSystem {
	LINUX,
	MAC,
	WINDOWS,
	UNKNOWN;

	private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
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

