package org.cryptomator.jfuse.tests;

import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.api.FuseBuilder;
import org.cryptomator.jfuse.api.FuseMountFailedException;
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
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@EnabledIf("hasSupportedImplementation")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MirrorIT {

	static {
		System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "TRACE");
	}

	// skip integration tests, if no implementation is available
	static boolean hasSupportedImplementation() {
		try {
			FuseBuilder.getSupported();
			return true;
		} catch (UnsupportedOperationException e) {
			return false;
		}
	}

	private Path orig;
	private Path mirror;
	private Fuse fuse;

	@BeforeAll
	public void setup(@TempDir Path tmpDir) throws IOException, FuseMountFailedException {
		var builder = Fuse.builder();
		var libPath = System.getProperty("fuse.lib.path");
		if (libPath != null && !libPath.isEmpty()) {
			builder.setLibraryPath(libPath);
		}
		List<String> flags = new ArrayList<>();
		//flags.add("-s");
		mirror = tmpDir.resolve("mirror");
		orig = tmpDir.resolve("orig");
		Files.createDirectories(orig);
		AbstractMirrorFileSystem fs = switch (OS.current()) {
			case WINDOWS -> {
				flags.add("-ouid=-1");
				flags.add("-ogid=-1");
				yield new WindowsMirrorFileSystem(orig, builder.errno());
			}
			case MAC, LINUX -> {
				Files.createDirectories(mirror);
				yield new PosixMirrorFileSystem(orig, builder.errno());
			}
			default -> throw new FuseMountFailedException("Unsupported OS");
		};
		fuse = builder.build(fs);
		fuse.mount("mirror-it", mirror, flags.toArray(String[]::new));
	}

	@AfterAll
	public void teardown() throws IOException, InterruptedException {
		// attempt graceful unmount before closing
		switch (OS.current()) {
			case MAC -> {
				ProcessBuilder command = new ProcessBuilder("umount", "--", mirror.getFileName().toString());
				command.directory(mirror.getParent().toFile());
				Process p = command.start();
				p.waitFor(10, TimeUnit.SECONDS);
			}
			case LINUX -> {
				ProcessBuilder command = new ProcessBuilder("fusermount", "-u", "--", mirror.getFileName().toString());
				command.directory(mirror.getParent().toFile());
				Process p = command.start();
				p.waitFor(10, TimeUnit.SECONDS);
			}
			case WINDOWS -> {
				// there is no graceful unmount, see https://github.com/winfsp/winfsp/issues/121
			}
		}
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


	@Nested
	@DisabledOnOs(OS.WINDOWS) // see remark on https://github.com/cryptomator/jfuse/pull/26
	@TestInstance(TestInstance.Lifecycle.PER_CLASS)
	@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
	@DisplayName("Extended Attributes")
	public class TestXattr {

		private Path file;

		@BeforeAll
		public void setup() throws IOException {
			file = mirror.resolve("xattr.txt");
			Files.createFile(file);
		}

		@Order(1)
		@DisplayName("setxattr /xattr.txt")
		@ParameterizedTest(name = "{0}")
		@ValueSource(strings = {"attr1", "attr2", "attr3", "attr4", "attr5"})
		public void testSetxattr(String attrName) {
			var attrView = Files.getFileAttributeView(file, UserDefinedFileAttributeView.class);
			var attrValue = StandardCharsets.UTF_8.encode(attrName);

			Assertions.assertDoesNotThrow(() -> attrView.write(attrName, attrValue));
		}

		@Order(2)
		@Test
		@DisplayName("removexattr /xattr.txt")

		public void testRemovexattr() {
			var attrView = Files.getFileAttributeView(file, UserDefinedFileAttributeView.class);

			Assertions.assertDoesNotThrow(() -> attrView.delete("attr3"));
		}

		@Order(3)
		@Test
		@DisplayName("listxattr /xattr.txt")
		public void testListxattr() throws IOException {
			var attrView = Files.getFileAttributeView(file, UserDefinedFileAttributeView.class);
			var result = attrView.list();

			Assertions.assertAll(
					() -> Assertions.assertTrue(result.contains("attr1")),
					() -> Assertions.assertTrue(result.contains("attr2")),
					() -> Assertions.assertFalse(result.contains("attr3")),
					() -> Assertions.assertTrue(result.contains("attr4")),
					() -> Assertions.assertTrue(result.contains("attr5"))
			);
		}

		@Order(4)
		@DisplayName("getxattr")
		@ParameterizedTest(name = "{0}")
		@ValueSource(strings = {/* "attr1", BUG in fuse-t */ "attr2", "attr4", "attr5"})
		public void testGetxattr(String attrName) throws IOException {
			var attrView = Files.getFileAttributeView(file, UserDefinedFileAttributeView.class);
			var buffer = ByteBuffer.allocate(attrView.size(attrName));
			var read = attrView.read(attrName, buffer);
			buffer.flip();
			var value = StandardCharsets.UTF_8.decode(buffer).toString();

			Assertions.assertEquals(attrName.length(), read);
			Assertions.assertEquals(attrName, value);
		}

	}
}
