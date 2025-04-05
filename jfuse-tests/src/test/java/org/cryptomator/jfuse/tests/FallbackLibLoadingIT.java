package org.cryptomator.jfuse.tests;

import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.examples.RandomFileSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeoutException;

@EnabledIfSystemProperty(named = "fuse.lib.path", matches = ".+")
public class FallbackLibLoadingIT {

	@Test
	@EnabledOnOs(OS.LINUX)
	@DisplayName("loads fuse lib from java.library.path, if not calling FuseBuilder.setLibraryPath(...)")
	public void loadFromJavaLibraryPathOnLinux() throws IOException, TimeoutException {
		loadFromJavaLibraryPath("libfuse3.so");
	}

	@Test
	@EnabledOnOs(OS.MAC)
	@DisplayName("loads fuse lib from java.library.path, if not calling FuseBuilder.setLibraryPath(...)")
	public void loadFromJavaLibraryPathOnMacOS() throws IOException, TimeoutException {
		loadFromJavaLibraryPath("libfuse-t.dylib");
	}

	private void loadFromJavaLibraryPath(String libFileName) throws IOException, TimeoutException {
		// symlink ./${libName} -> ${fuse.lib.path}
		var symlinkPath = Path.of(System.getProperty("user.dir"), libFileName);
		Files.createSymbolicLink(symlinkPath, Path.of(System.getProperty("fuse.lib.path")));

		var builder = Fuse.builder();
		var fs = new RandomFileSystem(builder.errno());
		var fuse = Assertions.assertDoesNotThrow(() -> builder.build(fs));
		fuse.close();

		Files.delete(symlinkPath);
	}

}
