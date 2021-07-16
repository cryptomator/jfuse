package de.skymatic.fusepanama;

enum Platform {
	LINUX,
	MAC,
	WINDOWS,
	OTHER;

	private static final String OS_NAME = System.getProperty("os.name").toLowerCase();
	public static final Platform CURRENT = OS_NAME.contains("linux") ? LINUX
			: OS_NAME.contains("mac") ? MAC
			: OS_NAME.contains("windows") ? WINDOWS
			: OTHER;
}
