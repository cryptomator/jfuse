package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.FuseMountFailedException;
import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.api.TimeSpec;
import org.cryptomator.jfuse.mac.extr.fuse.fuse_file_info;
import org.cryptomator.jfuse.mac.extr.fuse.fuse_h;
import org.cryptomator.jfuse.mac.extr.fuse.stat;
import org.cryptomator.jfuse.mac.extr.fuse.timespec;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.time.Instant;
import java.util.List;

import static java.lang.foreign.ValueLayout.JAVA_INT;

public class FuseImplTest {

	private FuseOperations fuseOps = Mockito.mock(FuseOperations.class);
	private FuseImpl fuseImpl = new FuseImpl(fuseOps);

	@Nested
	@DisplayName("mount()")
	public class Mount {

		private List<String> args = List.of("foo", "bar");
		private FuseImpl fuseImplSpy = Mockito.spy(fuseImpl);
		private MockedStatic<fuse_h> fuseH;

		@BeforeEach
		public void setup() {
			Mockito.doReturn(Mockito.mock(FuseArgs.class)).when(fuseImplSpy).parseArgs(args);
			fuseH = Mockito.mockStatic(fuse_h.class);
		}

		@AfterEach
		public void teardown() {
			fuseH.close();
		}

		@Test
		@DisplayName("MountFailedException when fuse_new fails")
		public void testFuseNewFails() {
			fuseH.when(() -> fuse_h.fuse_mount(Mockito.any(), Mockito.any())).thenReturn(MemorySegment.ofAddress(42L));
			fuseH.when(() -> fuse_h.fuse_new(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.any())).thenReturn(MemorySegment.NULL);

			var thrown = Assertions.assertThrows(FuseMountFailedException.class, () -> fuseImplSpy.mount(args));

			fuseH.verify(() -> fuse_h.fuse_unmount(Mockito.any(), Mockito.any()));
			Assertions.assertEquals("fuse_new failed", thrown.getMessage());
		}

		@Test
		@DisplayName("MountFailedException when fuse_mount fails")
		public void testFuseMountFails() {
			fuseH.when(() -> fuse_h.fuse_mount(Mockito.any(), Mockito.any())).thenReturn(MemorySegment.NULL);

			var thrown = Assertions.assertThrows(FuseMountFailedException.class, () -> fuseImplSpy.mount(args));

			fuseH.verify(() -> fuse_h.fuse_new(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.any()), Mockito.never());
			Assertions.assertEquals("fuse_mount failed", thrown.getMessage());
		}

	}

	@Test
	@DisplayName("parseArgs")
	public void testParseArgs() {
		try (var fuseH = Mockito.mockStatic(fuse_h.class);
			var arena = Arena.ofConfined()) {
			fuseH.when(() -> fuse_h.fuse_parse_cmdline(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).then(invocation -> {
				MemorySegment mp = invocation.getArgument(1);
				MemorySegment mt = invocation.getArgument(2);
				MemorySegment fg = invocation.getArgument(3);
				mp.set(ValueLayout.ADDRESS, 0L, arena.allocateFrom("/mount/point"));
				mt.set(JAVA_INT, 0L, 1);
				fg.set(JAVA_INT, 0L, 1);
				return 0;
			});

			var fuseArgs = fuseImpl.parseArgs(List.of("fusefs", "-foo", "-bar", "/mount/point"));

			Assertions.assertTrue(fuseArgs.multiThreaded());
			Assertions.assertTrue(fuseArgs.toString().contains("arg[0] = fusefs"));
			Assertions.assertTrue(fuseArgs.toString().contains("arg[1] = -foo"));
			Assertions.assertTrue(fuseArgs.toString().contains("arg[2] = -bar"));
			Assertions.assertTrue(fuseArgs.toString().contains("mountPoint = /mount/point"));
		}
	}

	@Nested
	@DisplayName("flush/fsync/fsyncdir")
	public class FlushFsyncFsyncdir {

		@Test
		@DisplayName("flush(\"/foo\", fi)")
		public void testFlush() {
			try (var arena = Arena.ofConfined()) {
				var path = arena.allocateFrom("/foo");
				var fi = fuse_file_info.allocate(arena);
				Mockito.doReturn(42).when(fuseOps).flush(Mockito.eq("/foo"), Mockito.any());

				var result = fuseImpl.flush(path, fi);

				Assertions.assertEquals(42, result);
			}
		}

		@Test
		@DisplayName("fsync(\"/foo\", 1, fi)")
		public void testFsync() {
			try (var arena = Arena.ofConfined()) {
				var path = arena.allocateFrom("/foo");
				var fi = fuse_file_info.allocate(arena);
				Mockito.doReturn(42).when(fuseOps).fsync(Mockito.eq("/foo"), Mockito.eq(1), Mockito.any());

				var result = fuseImpl.fsync(path, 1, fi);

				Assertions.assertEquals(42, result);
			}
		}

		@Test
		@DisplayName("fsyncdir(\"/foo\", 1, fi)")
		public void testFsyncdir() {
			try (var arena = Arena.ofConfined()) {
				var path = arena.allocateFrom("/foo");
				var fi = fuse_file_info.allocate(arena);
				Mockito.doReturn(42).when(fuseOps).fsyncdir(Mockito.eq("/foo"), Mockito.eq(1), Mockito.any());

				var result = fuseImpl.fsyncdir(path, 1, fi);

				Assertions.assertEquals(42, result);
			}
		}

	}

	@Nested
	@DisplayName("utimens")
	public class Utimens {

		@DisplayName("utimens(\"/foo\", UTIME_NOW, UTIME_NOW)")
		@Test
		public void testUtimensNow() {
			try (var arena = Arena.ofConfined()) {
				var path = arena.allocateFrom("/foo");
				var times = MemorySegment.NULL;
				Mockito.doReturn(42).when(fuseOps).utimens(Mockito.eq("/foo"), Mockito.argThat(TimeSpec::isUtimeNow), Mockito.argThat(TimeSpec::isUtimeNow), Mockito.isNull());

				var result = fuseImpl.utimens(path, times);

				Assertions.assertEquals(42, result);
			}
		}

		@DisplayName("utimens(\"/foo\", ...)")
		@ParameterizedTest
		@CsvSource(value = {
				"123456,789, 456789,123",
				"111222,333, 444555,666",
		})
		public void testUtimens(long sec0, long nsec0, long sec1, long nsec1) {
			Instant expectedATime = Instant.ofEpochSecond(sec0, nsec0);
			Instant expectedMTime = Instant.ofEpochSecond(sec1, nsec1);
			try (var arena = Arena.ofConfined()) {
				var path = arena.allocateFrom("/foo");
				var times = timespec.allocateArray(2, arena);
				timespec.tv_sec(timespec.asSlice(times, 0), sec0);
				timespec.tv_nsec(timespec.asSlice(times, 0), nsec0);
				timespec.tv_sec(timespec.asSlice(times, 1), sec1);
				timespec.tv_nsec(timespec.asSlice(times, 1), nsec1);
				Mockito.doReturn(42).when(fuseOps).utimens(Mockito.eq("/foo"), Mockito.argThat(t -> expectedATime.equals(t.get())), Mockito.argThat(t -> expectedMTime.equals(t.get())), Mockito.isNull());

				var result = fuseImpl.utimens(path, times);

				Assertions.assertEquals(42, result);
			}
		}
	}

	@Nested
	@DisplayName("attr")
	public class Attr {

		@Test
		@DisplayName("getattr")
		public void testGetattr() {
			try (var arena = Arena.ofConfined()) {
				var path = arena.allocateFrom("/foo");
				var attr = stat.allocate(arena);
				Mockito.doReturn(42).when(fuseOps).getattr(Mockito.eq("/foo"), Mockito.any(), Mockito.isNull());

				var result = fuseImpl.getattr(path, attr);

				Assertions.assertEquals(42, result);
			}
		}

		@Test
		@DisplayName("fgetattr")
		public void testFgetattr() {
			try (var arena = Arena.ofConfined()) {
				var path = arena.allocateFrom("/foo");
				var attr = stat.allocate(arena);
				var fi = fuse_file_info.allocate(arena);
				Mockito.doReturn(42).when(fuseOps).getattr(Mockito.eq("/foo"), Mockito.any(), Mockito.notNull());

				var result = fuseImpl.fgetattr(path, attr, fi);

				Assertions.assertEquals(42, result);
			}
		}

		@Test
		@DisplayName("getxattr")
		public void testGetxattr() {
			try (var arena = Arena.ofConfined()) {
				var path = arena.allocateFrom("/foo");
				var name = arena.allocateFrom("bar");
				var value = arena.allocate(100);

				Mockito.doReturn(42).when(fuseOps).getxattr(Mockito.eq("/foo"), Mockito.eq("bar"), Mockito.any());

				var result = fuseImpl.getxattr(path, name, value, 100);

				Assertions.assertEquals(42, result);
			}
		}

		@Test
		@DisplayName("setxattr")
		public void testSetxattr() {
			try (var arena = Arena.ofConfined()) {
				var path = arena.allocateFrom("/foo");
				var name = arena.allocateFrom("bar");
				var value = arena.allocate(100);

				Mockito.doReturn(42).when(fuseOps).setxattr(Mockito.eq("/foo"), Mockito.eq("bar"), Mockito.any(), Mockito.anyInt());

				var result = fuseImpl.setxattr(path, name, value, 100, 0xDEADBEEF);

				Assertions.assertEquals(42, result);
			}
		}

		@Test
		@DisplayName("listxattr")
		public void testListxattr() {
			try (var arena = Arena.ofConfined()) {
				var path = arena.allocateFrom("/foo");
				var list = arena.allocate(100);

				Mockito.doReturn(42).when(fuseOps).listxattr(Mockito.eq("/foo"), Mockito.any());

				var result = fuseImpl.listxattr(path, list, 100);

				Assertions.assertEquals(42, result);
			}
		}

		@Test
		@DisplayName("removexattr")
		public void testRemovexattr() {
			try (var arena = Arena.ofConfined()) {
				var path = arena.allocateFrom("/foo");
				var name = arena.allocateFrom("bar");

				Mockito.doReturn(42).when(fuseOps).removexattr(Mockito.eq("/foo"), Mockito.eq("bar"));

				var result = fuseImpl.removexattr(path, name);

				Assertions.assertEquals(42, result);
			}
		}

	}

	@Nested
	@DisplayName("truncate")
	public class Truncate {

		@Test
		@DisplayName("truncate")
		public void testTruncate() {
			try (var arena = Arena.ofConfined()) {
				var path = arena.allocateFrom("/foo");
				Mockito.doReturn(42).when(fuseOps).truncate(Mockito.eq("/foo"), Mockito.eq(1337L), Mockito.isNull());

				var result = fuseImpl.truncate(path, 1337L);

				Assertions.assertEquals(42, result);
			}
		}

		@Test
		@DisplayName("ftruncate")
		public void testFtruncate() {
			try (var arena = Arena.ofConfined()) {
				var path = arena.allocateFrom("/foo");
				var fi = fuse_file_info.allocate(arena);
				Mockito.doReturn(42).when(fuseOps).truncate(Mockito.eq("/foo"), Mockito.eq(1337L), Mockito.notNull());

				var result = fuseImpl.ftruncate(path, 1337L, fi);

				Assertions.assertEquals(42, result);
			}
		}

	}

	@Test
	@DisplayName("chown")
	public void testChown() {
		try (var arena = Arena.ofConfined()) {
			var path = arena.allocateFrom("/foo");
			Mockito.doReturn(42).when(fuseOps).chown("/foo", 42, 1337, null);

			var result = fuseImpl.chown(path, 42, 1337);

			Assertions.assertEquals(42, result);
		}
	}

}