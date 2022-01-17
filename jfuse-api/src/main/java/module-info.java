import org.cryptomator.jfuse.FuseBuilder;

/**
 * Defines the API to create a FUSE file system in Java.
 */
module org.cryptomator.jfuse {
	requires jdk.incubator.foreign;
	requires static org.jetbrains.annotations;

	exports org.cryptomator.jfuse;

	uses FuseBuilder;
}