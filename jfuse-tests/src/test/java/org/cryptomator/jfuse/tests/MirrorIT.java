package org.cryptomator.jfuse.tests;

import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.examples.AbstractMirrorFileSystem;
import org.cryptomator.jfuse.examples.PosixMirrorFileSystem;
import org.cryptomator.jfuse.examples.WindowsMirrorFileSystem;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MirrorIT {

	private static final String WIN_MOUNT_DIR_PROP = "jfuse.win.integrationMountDir";

	private Path orig;
	private Path mirror;
	private Fuse fuse;

	@BeforeAll
	public void setup(@TempDir Path tmpDir) throws IOException, TimeoutException {
		var builder = Fuse.builder();
		orig = tmpDir.resolve("orig");
		Files.createDirectories(orig);
		AbstractMirrorFileSystem fs;
		List<String> flags = new ArrayList<>();
		flags.add("-s");
		mirror = tmpDir.resolve("mirror");
		if (OS.WINDOWS.isCurrentOs()) {
			flags.add("-ouid=-1");
			flags.add("-ogid=-1");
			fs = new WindowsMirrorFileSystem(orig, builder.errno());
		} else {
			Files.createDirectories(mirror);
			fs = new PosixMirrorFileSystem(orig, builder.errno());
		}
		fuse = builder.build(fs);
		int result = fuse.mount("mirror-it", mirror, flags.toArray(String[]::new));
		Assertions.assertEquals(0, result, "mount failed");
	}

	@AfterAll
	public void teardown() throws IOException, InterruptedException {
		if (OS.MAC.isCurrentOs()) {
			ProcessBuilder command = new ProcessBuilder("umount", "-f", "--", mirror.getFileName().toString());
			command.directory(mirror.getParent().toFile());
			Process p = command.start();
			p.waitFor(10, TimeUnit.SECONDS);
		} else if (OS.LINUX.isCurrentOs()) {
			ProcessBuilder command = new ProcessBuilder("fusermount", "-u", "--", mirror.getFileName().toString());
			command.directory(mirror.getParent().toFile());
			Process p = command.start();
			p.waitFor(10, TimeUnit.SECONDS);
		}
		// for Windows we call internally fuse_exit
		if (fuse != null) {
			Assertions.assertTimeoutPreemptively(Duration.ofSeconds(10), fuse::close, "file system still active");
		}
	}

	@Nested
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	@DisplayName("Test Scenario 1")
	public class TestScenario1 {

		@Order(1)
		@DisplayName("create files")
		@ParameterizedTest(name = "create file /{0}")
		@ValueSource(strings = {"file0", "file1", "file2", "file3", "file4"})
		public void testCreateFiles(String filename) {
			Assertions.assertDoesNotThrow(() -> {
				Files.writeString(mirror.resolve(filename), filename, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
			});
		}

		@Order(2)
		@DisplayName("create directories")
		@ParameterizedTest(name = "create dir /{0}")
		@ValueSource(strings = {"dir0", "dir1", "dir2"})
		public void testCreateDirs(String dirname) {
			Assertions.assertDoesNotThrow(() -> {
				Files.createDirectory(mirror.resolve(dirname));
			});
		}

		@Order(3)
		@DisplayName("move files to /dir0")
		@ParameterizedTest(name = "move /{0} -> /dir0/{0}")
		@ValueSource(strings = {"file0", "file1", "file2", "file3", "file4"})
		public void testMoveFile(String filename) {
			Assertions.assertDoesNotThrow(() -> {
				Files.move(mirror.resolve(filename), mirror.resolve("dir0").resolve(filename));
			});
		}

		@Order(3)
		@DisplayName("move /dir1 -> /dir0/subdir")
		@Test
		public void testMoveDir() {
			Assertions.assertDoesNotThrow(() -> {
				Files.move(mirror.resolve("dir1"), mirror.resolve("dir0").resolve("subdir"));
			});
		}

		@Order(4)
		@RepeatedTest(10)
		@DisplayName("list /dir0")
		public void testListDir0() throws IOException {
			try (var ds = Files.list(mirror.resolve("dir0"))) {
				var list = ds.map(Path::getFileName).map(Path::toString).sorted().toArray(String[]::new);

				Assertions.assertArrayEquals(new String[]{"file0", "file1", "file2", "file3", "file4", "subdir"}, list);
			}
		}


	}


}
