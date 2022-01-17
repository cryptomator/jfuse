package org.cryptomator.jfuse.api;

public enum OperatingSystem {
	LINUX,
	MAC,
	WINDOWS,
	UNKNOWN;

	private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
	public static final OperatingSystem CURRENT = OS_NAME.contains("linux") ? LINUX
			: OS_NAME.contains("mac") ? MAC
			: OS_NAME.contains("windows") ? WINDOWS
			: UNKNOWN;
}

