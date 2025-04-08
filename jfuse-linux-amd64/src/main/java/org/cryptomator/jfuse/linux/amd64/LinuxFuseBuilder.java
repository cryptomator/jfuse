package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.api.Errno;
import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.api.FuseBuilder;
import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.api.platforms.Architecture;
import org.cryptomator.jfuse.api.platforms.OperatingSystem;
import org.cryptomator.jfuse.api.platforms.SupportedPlatform;

/**
 * Builds FUSE file system instances on Linux (x86_64).
 */
@SupportedPlatform(os = OperatingSystem.LINUX, arch = Architecture.AMD64)
public class LinuxFuseBuilder implements FuseBuilder {

	private static final String DEFAULT_LIBNAME = "fuse3";
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
			System.loadLibrary(DEFAULT_LIBNAME);
		}
		return new FuseImpl(fuseOperations);
	}

}
