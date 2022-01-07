package de.skymatic.fusepanama;

public interface FuseProvider {

	/**
	 * @return <code>true</code> if able to create a FUSE file system.
	 */
	boolean isAvailable();

	/**
	 * Creates a FUSE file system instance
	 *
	 * @param fuseOperations The file system operations
	 * @return A mountable file system
	 */
	Fuse create(FuseOperations fuseOperations);
}
