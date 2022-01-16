package de.skymatic.fusepanama.win.amd64;

import de.skymatic.fusepanama.Architecture;
import de.skymatic.fusepanama.Errno;
import de.skymatic.fusepanama.Fuse;
import de.skymatic.fusepanama.FuseBuilder;
import de.skymatic.fusepanama.FuseOperations;
import de.skymatic.fusepanama.OperatingSystem;
import de.skymatic.fusepanama.SupportedPlatform;

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
