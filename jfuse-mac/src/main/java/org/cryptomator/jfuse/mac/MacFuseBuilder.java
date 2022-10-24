package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.Architecture;
import org.cryptomator.jfuse.api.Errno;
import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.api.FuseBuilder;
import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.api.OperatingSystem;
import org.cryptomator.jfuse.api.SupportedPlatform;

import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Builds FUSE file system instances on macOS.
 */
@SupportedPlatform(os = OperatingSystem.MAC, arch = Architecture.AMD64)
@SupportedPlatform(os = OperatingSystem.MAC, arch = Architecture.ARM64)
public class MacFuseBuilder implements FuseBuilder {

	private static final Errno ERRNO = new MacErrno();
	private String libraryPath;

	/**
	 * Creates a new MacFuseBuilder instance.
	 */
	public MacFuseBuilder() {
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
			System.loadLibrary("fuse");
		}
		return new FuseImpl(fuseOperations);
	}

}
