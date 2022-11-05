import org.cryptomator.jfuse.api.FuseBuilder;

/**
 * Defines the API to create a FUSE file system in Java.
 *
 * @uses FuseBuilder FUSE builder to build FUSE file system instances
 */
module org.cryptomator.jfuse.api {
	requires static org.jetbrains.annotations;

	exports org.cryptomator.jfuse.api;
	exports org.cryptomator.jfuse.api.platforms to org.cryptomator.jfuse.linux.aarch64, org.cryptomator.jfuse.linux.amd64, org.cryptomator.jfuse.mac, org.cryptomator.jfuse.win;

	uses FuseBuilder;
}