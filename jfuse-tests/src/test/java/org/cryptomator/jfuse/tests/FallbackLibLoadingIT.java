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
	public void loadFromJavaLibraryPathOnLinux(@TempDir Path p) throws IOException, TimeoutException {
		loadFromJavaLibraryPath(p, "libfuse3.so");
	}

	@Test
	@EnabledOnOs(OS.MAC)
	@DisplayName("loads fuse lib from java.library.path, if not calling FuseBuilder.setLibraryPath(...)")
	public void loadFromJavaLibraryPathOnMacOS(@TempDir Path p) throws IOException, TimeoutException {
		loadFromJavaLibraryPath(p, "libfuse-t.dylib");
	}

	private void loadFromJavaLibraryPath(Path tmpLibPath, String libFileName) throws IOException, TimeoutException {
		var libPath = System.getProperty("fuse.lib.path");
		var symlinkPath = tmpLibPath.resolve(libFileName);
		Files.createSymbolicLink(symlinkPath, Path.of(libPath));

		System.setProperty("java.library.path", tmpLibPath.toString());

		var builder = Fuse.builder();
		var fs = new RandomFileSystem(builder.errno());
		var fuse = Assertions.assertDoesNotThrow(() -> builder.build(fs));
		fuse.close();
	}

}
