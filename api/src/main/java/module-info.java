import de.skymatic.fusepanama.Errno;
import de.skymatic.fusepanama.FuseProvider;

/**
 * Defines the API to create a FUSE file system in Java.
 */
module de.skymatic.fusepanama {
	requires jdk.incubator.foreign;

	exports de.skymatic.fusepanama;

	uses Errno;
	uses FuseProvider;
}