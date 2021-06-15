package de.skymatic.fusepanama;

class Platform {
	public static final String OS_NAME = System.getProperty("os.name").toLowerCase();
	public static boolean IS_MAC = OS_NAME.contains("mac");
	public static boolean IS_WINDOWS = OS_NAME.contains("windows");
	public static boolean IS_LINUX = OS_NAME.contains("linux");
}
