package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.Errno;
import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.api.FuseBuilder;
import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.api.platforms.Architecture;
import org.cryptomator.jfuse.api.platforms.OperatingSystem;
import org.cryptomator.jfuse.api.platforms.SupportedPlatform;

/**
 * Builds FUSE file system instances on macOS.
 */
@SupportedPlatform(os = OperatingSystem.MAC, arch = Architecture.AMD64)
@SupportedPlatform(os = OperatingSystem.MAC, arch = Architecture.ARM64)
public class MacFuseBuilder implements FuseBuilder {

	private static final String DEFAULT_MACFUSE_LIBNAME = "fuse";
	private static final String DEFAULT_FUSET_LIBNAMNE = "fuse-t";
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
			try {
				System.loadLibrary(DEFAULT_MACFUSE_LIBNAME);
			} catch (UnsatisfiedLinkError errorLoadingMacFuse) {
				try {
					System.loadLibrary(DEFAULT_FUSET_LIBNAMNE);
				} catch (UnsatisfiedLinkError errorLoadingFuseT) {
					errorLoadingFuseT.addSuppressed(errorLoadingMacFuse);
					throw errorLoadingFuseT;
				}
			}
		}
		return new FuseImpl(fuseOperations);
	}

}
