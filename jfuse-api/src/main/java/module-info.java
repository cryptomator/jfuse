import org.cryptomator.jfuse.api.FuseBuilder;

/**
 * Defines the API to create a FUSE file system in Java.
 *
 * @uses FuseBuilder FUSE builder to build FUSE file system instances
 */
module org.cryptomator.jfuse.api {
	requires static org.jetbrains.annotations;

	exports org.cryptomator.jfuse.api;

	uses FuseBuilder;
}