package org.cryptomator.jfuse.tests;

import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.api.MountFailedException;
import org.cryptomator.jfuse.examples.AbstractMirrorFileSystem;
import org.cryptomator.jfuse.examples.PosixMirrorFileSystem;
import org.cryptomator.jfuse.examples.WindowsMirrorFileSystem;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
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
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MirrorIT {

	static {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "TRACE");
	}

	private Path orig;
	private Path mirror;
	private Fuse fuse;

	@BeforeAll
	public void setup(@TempDir Path tmpDir) throws IOException, InterruptedException, MountFailedException {
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
		fuse.mount("mirror-it", mirror, flags.toArray(String[]::new));
		Thread.sleep(100); // give the file system some time to accept the mounted volume
	}

	@AfterAll
	public void teardown() throws IOException, InterruptedException {
		// attempt graceful unmount before closing
		if (OS.MAC.isCurrentOs()) {
			ProcessBuilder command = new ProcessBuilder("umount", "--", mirror.getFileName().toString());
			command.directory(mirror.getParent().toFile());
			Process p = command.start();
			p.waitFor(10, TimeUnit.SECONDS);
		} else if (OS.LINUX.isCurrentOs()) {
			ProcessBuilder command = new ProcessBuilder("fusermount", "-u", "--", mirror.getFileName().toString());
			command.directory(mirror.getParent().toFile());
			Process p = command.start();
			p.waitFor(10, TimeUnit.SECONDS);
		}
		// TODO add win-specific unmount code?
		if (fuse != null) {
			Assertions.assertTimeoutPreemptively(Duration.ofSeconds(10), fuse::close, "file system still active");
		}
		Thread.sleep(100); // give the file system some time before cleaning up the @TempDir
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

		@Order(5)
		@DisplayName("touch /dir0/file0")
		@Test
		public void testChangeLastModifiedTime() throws IOException {
			var file0 = mirror.resolve("dir0/file0");
			var timeW = FileTime.fromMillis(1645484400000L);
			Files.setLastModifiedTime(file0, timeW);
			var timeR = Files.getLastModifiedTime(file0);
			Assertions.assertEquals(timeW, timeR);
		}

		@Order(6)
		@DisplayName("truncate /dir0/file0")
		@Test
		public void testTruncate() throws IOException {
			var file0 = mirror.resolve("dir0/file0");
			try (var ch = FileChannel.open(file0, StandardOpenOption.WRITE)) {
				ch.truncate(2);
			}
			Assertions.assertEquals(2, Files.size(file0));
		}

		@Order(7)
		@DisplayName("read files")
		@ParameterizedTest(name = "read file /dir0/{0}")
		@ValueSource(strings = {"file1", "file2", "file3", "file4"})
		public void testReadFiles(String filename) throws IOException {
			var content = Files.readString(mirror.resolve("dir0").resolve(filename));
			Assertions.assertEquals(filename, content);
		}

	}


}
