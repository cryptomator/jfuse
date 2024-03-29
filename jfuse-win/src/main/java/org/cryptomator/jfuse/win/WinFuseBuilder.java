package org.cryptomator.jfuse.win;

import org.cryptomator.jfuse.api.Errno;
import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.api.FuseBuilder;
import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.api.platforms.Architecture;
import org.cryptomator.jfuse.api.platforms.OperatingSystem;
import org.cryptomator.jfuse.api.platforms.SupportedPlatform;

/**
 * Builds FUSE file system instances on Windows.
 */
@SupportedPlatform(os = OperatingSystem.WINDOWS, arch = Architecture.AMD64)
@SupportedPlatform(os = OperatingSystem.WINDOWS, arch = Architecture.ARM64)
public class WinFuseBuilder implements FuseBuilder {

	private static final String DEFAULT_LIB_PATH_AMD64 = "C:\\Program Files (x86)\\WinFsp\\bin\\winfsp-x64.dll";
	private static final String DEFAULT_LIB_PATH_ARM64 = "C:\\Program Files (x86)\\WinFsp\\bin\\winfsp-a64.dll";
	private static final Errno ERRNO = new WinErrno();
	private String libraryPath;

	/**
	 * Creates a new WinFuseBuilder instance.
	 */
	public WinFuseBuilder() {
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
		} else if (Architecture.CURRENT == Architecture.AMD64) {
			System.load(DEFAULT_LIB_PATH_AMD64);
		} else if (Architecture.CURRENT == Architecture.ARM64) {
			System.load(DEFAULT_LIB_PATH_ARM64);
		}
		return new FuseImpl(fuseOperations);
	}

}
