package org.cryptomator.jfuse.api;

import org.cryptomator.jfuse.api.platforms.Architecture;
import org.cryptomator.jfuse.api.platforms.OperatingSystem;
import org.cryptomator.jfuse.api.platforms.SupportedPlatform;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.ServiceLoader;

/**
 * A platform-specific builder to create a FUSE file system.
 */
public interface FuseBuilder {

	/**
	 * Find a FuseBuilder that is {@link SupportedPlatform supported on this platform}.
	 *
	 * @return Any supported builder
	 * @throws UnsupportedOperationException Thrown if no supported builder could be found.
	 */
	static FuseBuilder getSupported() throws UnsupportedOperationException {
		return ServiceLoader.load(FuseBuilder.class)
				.stream()
				.filter(FuseBuilder::isSupported)
				.findAny()
				.map(ServiceLoader.Provider::get)
				.orElseThrow(() -> new UnsupportedOperationException("No implementation of FuseProvider found for the current platform."));
	}

	private static boolean isSupported(ServiceLoader.Provider<FuseBuilder> provider) {
		return Arrays.stream(provider.type().getAnnotationsByType(SupportedPlatform.class))
				.filter(platform -> OperatingSystem.CURRENT.equals(platform.os()))
				.anyMatch(platform -> Architecture.CURRENT.equals(platform.arch()));
	}

	/**
	 * The error constants used on the selected platform.
	 *
	 * @return The <code>errno.h</code> error codes
	 */
	Errno errno();

	/**
	 * Sets the location from which to load the fuse library. If omitted, the library may still be found on the library search path.
	 *
	 * @param libraryPath Location of the fuse library. May be <code>null</code> to try to load the library from default locations.
	 */
	void setLibraryPath(@Nullable String libraryPath);

	/**
	 * Creates a FUSE file system instance
	 *
	 * @param fuseOperations The file system operations
	 * @return A mountable file system
	 * @throws UnsatisfiedLinkError In case of errors while loading the fuse library
	 */
	Fuse build(FuseOperations fuseOperations) throws UnsatisfiedLinkError;
}
