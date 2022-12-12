package org.cryptomator.jfuse.win;

import org.cryptomator.jfuse.api.FileInfo;
import org.cryptomator.jfuse.api.Fuse;
import org.cryptomator.jfuse.api.FuseConnInfo;
import org.cryptomator.jfuse.api.FuseMount;
import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.api.FuseMountFailedException;
import org.cryptomator.jfuse.win.extr.fuse2.fuse2_h;
import org.cryptomator.jfuse.win.extr.fuse3_config;
import org.cryptomator.jfuse.win.extr.fuse3_conn_info;
import org.cryptomator.jfuse.win.extr.fuse3_file_info;
import org.cryptomator.jfuse.win.extr.fuse_h;
import org.cryptomator.jfuse.win.extr.fuse_stat;
import org.cryptomator.jfuse.win.extr.fuse_timespec;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentMatcher;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.lang.foreign.ValueLayout;
import java.nio.file.Path;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.foreign.ValueLayout.JAVA_INT;

public class FuseImplTest {

	private FuseOperations fuseOps = Mockito.mock(FuseOperations.class);
	private FuseImpl fuseImpl;

	@BeforeEach
	public void setup() {
		// required to mock protected methods:
		class MockableFuseImpl extends FuseImpl {
			public MockableFuseImpl(FuseOperations fuseOperations) {
				super(fuseOperations);
			}
		}

		this.fuseImpl = new MockableFuseImpl(fuseOps);
	}

	@Nested
	@DisplayName("mount()")
	public class Mount {

		private List<String> args = List.of("foo", "bar");
		private FuseImpl fuseImplSpy;
		private MockedStatic<fuse_h> fuseH;

		@BeforeEach
		public void setup() {
			fuseImplSpy = Mockito.spy(fuseImpl);
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
			fuseH.when(() -> fuse_h.fuse3_new(Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.any())).thenReturn(MemoryAddress.NULL);

			var thrown = Assertions.assertThrows(FuseMountFailedException.class, () -> fuseImplSpy.mount(args));

			fuseH.verify(() -> fuse_h.fuse3_mount(Mockito.any(), Mockito.any()), Mockito.never());
			Assertions.assertEquals("fuse_new failed", thrown.getMessage());
		}

		@Test
		@DisplayName("MountFailedException when fuse_mount fails")
		public void testFuseMountFails() {
			fuseH.when(() -> fuse_h.fuse3_new(Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.any())).thenReturn(MemoryAddress.ofLong(42L));
			fuseH.when(() -> fuse_h.fuse3_mount(Mockito.any(), Mockito.any())).thenReturn(1);

			var thrown = Assertions.assertThrows(FuseMountFailedException.class, () -> fuseImplSpy.mount(args));

			Assertions.assertEquals("fuse_mount failed", thrown.getMessage());
		}

		@Test
		@DisplayName("Adjust mount point A -> A:")
		public void testAdjustMountPoint() throws FuseMountFailedException, InterruptedException {
			Path mountPoint = Mockito.mock(Path.class, "A");
			FuseMount fuseMount = Mockito.mock(FuseMount.class);
			Mockito.doReturn(mountPoint).when(mountPoint).getRoot();
			Mockito.doReturn(true).when(mountPoint).isAbsolute();
			Mockito.doNothing().when((Fuse) fuseImplSpy).mount(Mockito.any(), Mockito.any(), Mockito.any());

			fuseImplSpy.mount("test", mountPoint);

			Mockito.verify((Fuse) fuseImplSpy).mount(Mockito.any(), Mockito.eq(mountPoint), Mockito.any());
		}
	}

	@Test
	@DisplayName("parseArgs")
	public void testParseArgs() {
		try (var fuseH = Mockito.mockStatic(fuse2_h.class);
			 var scope = MemorySession.openConfined()) {
			fuseH.when(() -> fuse2_h.fuse_parse_cmdline(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).then(invocation -> {
				MemorySegment mp = invocation.getArgument(1);
				MemorySegment mt = invocation.getArgument(2);
				MemorySegment fg = invocation.getArgument(3);
				mp.set(ValueLayout.ADDRESS, 0L, scope.allocateUtf8String("/mount/point"));
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
			try (var scope = MemorySession.openConfined()) {
				var path = scope.allocateUtf8String("/foo");
				var fi = fuse3_file_info.allocate(scope);
				Mockito.doReturn(42).when(fuseOps).flush(Mockito.eq("/foo"), Mockito.any());

				var result = fuseImpl.flush(path.address(), fi.address());

				Assertions.assertEquals(42, result);
			}
		}

		@Test
		@DisplayName("fsync(\"/foo\", 1, fi)")
		public void testFsync() {
			try (var scope = MemorySession.openConfined()) {
				var path = scope.allocateUtf8String("/foo");
				var fi = fuse3_file_info.allocate(scope);
				Mockito.doReturn(42).when(fuseOps).fsync(Mockito.eq("/foo"), Mockito.eq(1), Mockito.any());

				var result = fuseImpl.fsync(path.address(), 1, fi.address());

				Assertions.assertEquals(42, result);
			}
		}

		@Test
		@DisplayName("fsyncdir(\"/foo\", 1, fi)")
		public void testFsyncdir() {
			try (var scope = MemorySession.openConfined()) {
				var path = scope.allocateUtf8String("/foo");
				var fi = fuse3_file_info.allocate(scope);
				Mockito.doReturn(42).when(fuseOps).fsyncdir(Mockito.eq("/foo"), Mockito.eq(1), Mockito.any());

				var result = fuseImpl.fsyncdir(path.address(), 1, fi.address());

				Assertions.assertEquals(42, result);
			}
		}

	}

	@DisplayName("init() sets fuse_conn_info.wants |= FUSE_CAP_READDIRPLUS")
	@Test
	public void testInit() {
		try (var scope = MemorySession.openConfined()) {
			var result = new AtomicInteger();
			Mockito.doAnswer(invocation -> {
				FuseConnInfo connInfo = invocation.getArgument(0);
				result.set(connInfo.want());
				return null;
			}).when(fuseOps).init(Mockito.any(), Mockito.any());
			var connInfo = fuse3_conn_info.allocate(scope);
			var fuseConfig = fuse3_config.allocate(scope);

			fuseImpl.init(connInfo.address(), fuseConfig.address());

			Assertions.assertEquals(FuseConnInfo.FUSE_CAP_READDIRPLUS, result.get() & FuseConnInfo.FUSE_CAP_READDIRPLUS);
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
		try (var scope = MemorySession.openConfined()) {
			var path = scope.allocateUtf8String("/foo");
			var times = fuse_timespec.allocateArray(2, scope);
			var fi = scope.allocate(fuse3_file_info.$LAYOUT());
			fuse_timespec.tv_sec$set(times, 0, sec0);
			fuse_timespec.tv_nsec$set(times, 0, nsec0);
			fuse_timespec.tv_sec$set(times, 1, sec1);
			fuse_timespec.tv_nsec$set(times, 1, nsec1);
			Mockito.doReturn(42).when(fuseOps).utimens(Mockito.eq("/foo"), Mockito.argThat(t -> expectedATime.equals(t.get())), Mockito.argThat(t -> expectedMTime.equals(t.get())), Mockito.argThat(usesSameMemorySegement(fi)));

			var result = fuseImpl.utimens(path.address(), times.address(), fi.address());

			Assertions.assertEquals(42, result);
		}
	}

	@Nested
	@DisplayName("attr")
	public class Attr {

		@Test
		@DisplayName("getattr")
		public void testGetattr() {
			try (var scope = MemorySession.openConfined()) {
				var path = scope.allocateUtf8String("/foo");
				var attr = fuse_stat.allocate(scope);
				var fi = scope.allocate(fuse3_file_info.$LAYOUT());
				Mockito.doReturn(42).when(fuseOps).getattr(Mockito.eq("/foo"), Mockito.any(), Mockito.argThat(usesSameMemorySegement(fi)));

				var result = fuseImpl.getattr(path.address(), attr.address(), fi.address());

				Assertions.assertEquals(42, result);
			}
		}

		@Test
		@DisplayName("getxattr")
		public void testGetxattr() {
			try (var scope = MemorySession.openConfined()) {
				var path = scope.allocateUtf8String("/foo");
				var name = scope.allocateUtf8String("bar");
				var value = scope.allocate(100);

				Mockito.doReturn(42).when(fuseOps).getxattr(Mockito.eq("/foo"), Mockito.eq("bar"), Mockito.any());

				var result = fuseImpl.getxattr(path.address(), name.address(), value.address(), 100);

				Assertions.assertEquals(42, result);
			}
		}

		@Test
		@DisplayName("setxattr")
		public void testSetxattr() {
			try (var scope = MemorySession.openConfined()) {
				var path = scope.allocateUtf8String("/foo");
				var name = scope.allocateUtf8String("bar");
				var value = scope.allocate(100);

				Mockito.doReturn(42).when(fuseOps).setxattr(Mockito.eq("/foo"), Mockito.eq("bar"), Mockito.any(), Mockito.anyInt());

				var result = fuseImpl.setxattr(path.address(), name.address(), value.address(), 100, 0xDEADBEEF);

				Assertions.assertEquals(42, result);
			}
		}

		@Test
		@DisplayName("listxattr")
		public void testListxattr() {
			try (var scope = MemorySession.openConfined()) {
				var path = scope.allocateUtf8String("/foo");
				var list = scope.allocate(100);

				Mockito.doReturn(42).when(fuseOps).listxattr(Mockito.eq("/foo"), Mockito.any());

				var result = fuseImpl.listxattr(path.address(), list.address(), 100);

				Assertions.assertEquals(42, result);
			}
		}

		@Test
		@DisplayName("removexattr")
		public void testRemovexattr() {
			try (var scope = MemorySession.openConfined()) {
				var path = scope.allocateUtf8String("/foo");
				var name = scope.allocateUtf8String("bar");

				Mockito.doReturn(42).when(fuseOps).removexattr(Mockito.eq("/foo"), Mockito.eq("bar"));

				var result = fuseImpl.removexattr(path.address(), name.address());

				Assertions.assertEquals(42, result);
			}
		}

	}


	@Test
	@DisplayName("truncate")
	public void testTruncate() {
		try (var scope = MemorySession.openConfined()) {
			var path = scope.allocateUtf8String("/foo");
			var fi = scope.allocate(fuse3_file_info.$LAYOUT());
			Mockito.doReturn(42).when(fuseOps).truncate(Mockito.eq("/foo"), Mockito.eq(1337L), Mockito.argThat(usesSameMemorySegement(fi)));

			var result = fuseImpl.truncate(path.address(), 1337L, fi.address());

			Assertions.assertEquals(42, result);
		}
	}

	@Test
	@DisplayName("chown")
	public void testChown() {
		try (var scope = MemorySession.openConfined()) {
			var path = scope.allocateUtf8String("/foo");
			var fi = fuse3_file_info.allocate(scope);
			Mockito.doReturn(42).when(fuseOps).chown(Mockito.eq("/foo"), Mockito.eq(42), Mockito.eq(1337), Mockito.any());

			var result = fuseImpl.chown(path.address(), 42, 1337, fi.address());

			Assertions.assertEquals(42, result);
		}
	}

	private static ArgumentMatcher<FileInfo> usesSameMemorySegement(MemorySegment expected) {
		return obj -> {
			if (obj instanceof FileInfoImpl fiImpl) {
				return fiImpl.segment().address().equals(expected.address()) && fiImpl.segment().byteSize() == expected.byteSize();
			}
			return false;
		};
	}

}