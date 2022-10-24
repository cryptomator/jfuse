package org.cryptomator.jfuse.linux.aarch64;

import org.cryptomator.jfuse.api.Architecture;
import org.cryptomator.jfuse.api.Errno;
import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.api.FuseBuilder;
import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.api.OperatingSystem;
import org.cryptomator.jfuse.api.SupportedPlatform;

/**
 * Builds FUSE file system instances on Linux (aarch64).
 */
@SupportedPlatform(os = OperatingSystem.LINUX, arch = Architecture.ARM64)
public class LinuxFuseBuilder implements FuseBuilder {

	private static final String DEFAULT_LIB_PATH = "/lib/aarch64-linux-gnu/libfuse3.so.3";
	private static final Errno ERRNO = new LinuxErrno();
	private String libraryPath;

	/**
	 * Creates a new LinuxFuseBuilder instance.
	 */
	public LinuxFuseBuilder() {
	}

	@Override
	public Errno errno() {
		return ERRNO;
	}

	@Override
	public void setLibraryPath(String libraryPath) {
		this.libraryPath = libraryPath;
	}

	@Override
	public Fuse build(FuseOperations fuseOperations) throws UnsatisfiedLinkError {
		if (libraryPath != null) {
			System.load(libraryPath);
		} else {
			System.load(DEFAULT_LIB_PATH);
		}
		return new FuseImpl(fuseOperations);
	}

}
