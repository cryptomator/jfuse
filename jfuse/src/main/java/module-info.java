/**
 * This is just an aggregator module. Please refer to the API module.
 */
module org.cryptomator.jfuse {
	requires transitive org.cryptomator.jfuse.api;

	requires org.cryptomator.jfuse.linux.aarch64;
	requires org.cryptomator.jfuse.linux.amd64;
	requires org.cryptomator.jfuse.mac;
	requires org.cryptomator.jfuse.win;
}