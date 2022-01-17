package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.Architecture;
import org.cryptomator.jfuse.Errno;
import org.cryptomator.jfuse.Fuse;
import org.cryptomator.jfuse.FuseBuilder;
import org.cryptomator.jfuse.FuseOperations;
import org.cryptomator.jfuse.OperatingSystem;
import org.cryptomator.jfuse.SupportedPlatform;

@SupportedPlatform(os = OperatingSystem.MAC, arch = Architecture.AMD64)
@SupportedPlatform(os = OperatingSystem.MAC, arch = Architecture.ARM64)
public class MacFuseBuilder implements FuseBuilder {

	private static final Errno ERRNO = new MacErrno();
	private String libraryPath;

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
