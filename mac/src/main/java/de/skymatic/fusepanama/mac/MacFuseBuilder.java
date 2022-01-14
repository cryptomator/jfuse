package de.skymatic.fusepanama.mac;

import de.skymatic.fusepanama.Architecture;
import de.skymatic.fusepanama.Errno;
import de.skymatic.fusepanama.Fuse;
import de.skymatic.fusepanama.FuseBuilder;
import de.skymatic.fusepanama.FuseOperations;
import de.skymatic.fusepanama.OperatingSystem;
import de.skymatic.fusepanama.SupportedPlatform;

import java.nio.file.Path;

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
