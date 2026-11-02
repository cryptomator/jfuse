package org.cryptomator.jfuse.tests;

import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.examples.WindowsMirrorFileSystem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Regression test for <a href="https://github.com/cryptomator/jfuse/issues/176">#176</a>: freeing <code>struct fuse</code>
 * before the run loop has returned crashed the JVM with an access violation, usually within the first few cycles.
 */
@EnabledOnOs(OS.WINDOWS)
public class UnmountStressIT {

	private static final int CYCLES = 30;
	private static final int PAYLOAD_SIZE = 200 * 1024;

	@Test
	@DisplayName("repeatedly mount, use and close a file system")
	public void testMountUnmountCycles(@TempDir Path tmpDir) throws Exception {
		var orig = tmpDir.resolve("orig");
		Files.createDirectories(orig);
		var payload = new byte[PAYLOAD_SIZE];
		new Random(42).nextBytes(payload);

		for (int i = 0; i < CYCLES; i++) {
			var mirror = tmpDir.resolve("mirror" + i);
			var builder = Fuse.builder();
			var libPath = System.getProperty("fuse.lib.path");
			if (libPath != null && !libPath.isEmpty()) {
				builder.setLibraryPath(libPath);
			}
			var fuse = builder.build(new WindowsMirrorFileSystem(orig, builder.errno()));
			fuse.mount("unmount-stress-it", mirror, "-ouid=-1", "-ogid=-1");
			try {
				var file = mirror.resolve("file" + i);
				Files.write(file, payload);
				Assertions.assertArrayEquals(payload, Files.readAllBytes(file));
				try (var dir = Files.list(mirror)) {
					Assertions.assertTrue(dir.findAny().isPresent());
				}
				Files.delete(file);
			} finally {
				int cycle = i;
				Assertions.assertTimeoutPreemptively(Duration.ofSeconds(10), fuse::close, "file system still active after cycle " + cycle);
			}
		}
		TimeUnit.MILLISECONDS.sleep(100); // give the file systems some time before cleaning up the @TempDir
	}

}
