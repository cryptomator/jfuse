import de.skymatic.fusepanama.Errno;
import de.skymatic.fusepanama.FileModes;
import de.skymatic.fusepanama.FuseProvider;
import de.skymatic.fusepanama.OpenFlags;

/**
 * Defines the API to create a FUSE file system in Java.
 */
module de.skymatic.fusepanama {
	requires jdk.incubator.foreign;
	requires static org.jetbrains.annotations;

	exports de.skymatic.fusepanama;

	uses Errno;
	uses FuseProvider;
	uses FileModes;
	uses OpenFlags;
}