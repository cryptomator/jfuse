/**
 * This is just an aggregator module. Please refer to the {@link org.cryptomator.jfuse.api/ API module}.
 */
module org.cryptomator.jfuse {
	requires transitive org.cryptomator.jfuse.api;

	requires org.cryptomator.jfuse.linux.aarch64;
	requires org.cryptomator.jfuse.linux.amd64;
	requires org.cryptomator.jfuse.mac;
	requires org.cryptomator.jfuse.win;

	// required for maven-javadoc-plugin to work:
	requires static org.jetbrains.annotations;
}