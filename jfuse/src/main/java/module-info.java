/**
 * Defines the API to create a FUSE file system in Java.
 */
module org.cryptomator.jfuse {
	requires transitive org.cryptomator.jfuse.api;
	requires org.cryptomator.jfuse.linux.aarch64;
	requires org.cryptomator.jfuse.linux.amd64;
	requires org.cryptomator.jfuse.mac;
	requires org.cryptomator.jfuse.win.amd64;
}