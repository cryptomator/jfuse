package org.cryptomator.jfuse;

public enum Architecture {
	AMD64,
	ARM64,
	UNKNOWN;

	private static final String OS_ARCH = System.getProperty("os.arch").toLowerCase();
	public static final Architecture CURRENT = OS_ARCH.contains("x86_64") ? AMD64 //
			: OS_ARCH.contains("amd64") ? AMD64 //
			: OS_ARCH.contains("aarch64") ? ARM64 //
			: UNKNOWN;
}

