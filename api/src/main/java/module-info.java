import de.skymatic.fusepanama.FuseBuilder;

/**
 * Defines the API to create a FUSE file system in Java.
 */
module de.skymatic.fusepanama {
	requires jdk.incubator.foreign;
	requires static org.jetbrains.annotations;

	exports de.skymatic.fusepanama;

	uses FuseBuilder;
}