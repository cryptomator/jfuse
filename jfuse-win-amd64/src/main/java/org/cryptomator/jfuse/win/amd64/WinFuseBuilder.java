package org.cryptomator.jfuse.win.amd64;

import org.cryptomator.jfuse.api.Architecture;
import org.cryptomator.jfuse.api.Errno;
import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.api.FuseBuilder;
import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.api.OperatingSystem;
import org.cryptomator.jfuse.api.SupportedPlatform;

@SupportedPlatform(os = OperatingSystem.WINDOWS, arch = Architecture.AMD64)
public class WinFuseBuilder implements FuseBuilder {

	private static final String DEFAULT_LIB_PATH = "C:\\Program Files (x86)\\WinFsp\\bin\\winfsp-x64.dll";
	private static final Errno ERRNO = new WinErrno();
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
			System.load(DEFAULT_LIB_PATH);
		}
		return new FuseImpl(fuseOperations);
	}

}
