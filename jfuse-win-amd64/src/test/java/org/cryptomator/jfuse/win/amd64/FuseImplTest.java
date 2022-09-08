package org.cryptomator.jfuse.win.amd64;

import org.cryptomator.jfuse.api.FileInfo;
import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.api.MountFailedException;
import org.cryptomator.jfuse.win.amd64.extr.fuse3_file_info;
import org.cryptomator.jfuse.win.amd64.extr.fuse_h;
import org.cryptomator.jfuse.win.amd64.extr.fuse_stat;
import org.cryptomator.jfuse.win.amd64.extr.fuse_timespec;
import org.cryptomator.jfuse.win.amd64.extr_fuse2.fuse_2_h;
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
			fuseH.when(() -> fuse_h.fuse3_new(Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.any())).thenReturn(MemoryAddress.NULL);

			var thrown = Assertions.assertThrows(MountFailedException.class, () -> fuseImplSpy.mount(args));

			fuseH.verify(() -> fuse_h.fuse3_mount(Mockito.any(), Mockito.any()), Mockito.never());
			Assertions.assertEquals("fuse_new failed", thrown.getMessage());
		}

		@Test
		@DisplayName("MountFailedException when fuse_mount fails")
		public void testFuseMountFails() {
			fuseH.when(() -> fuse_h.fuse3_new(Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.any())).thenReturn(MemoryAddress.ofLong(42L));
			fuseH.when(() -> fuse_h.fuse3_mount(Mockito.any(), Mockito.any())).thenReturn(1);

			var thrown = Assertions.assertThrows(MountFailedException.class, () -> fuseImplSpy.mount(args));

			Assertions.assertEquals("fuse_mount failed", thrown.getMessage());
		}
	}

	@Test
	@DisplayName("parseArgs")
	public void testParseArgs() {
		try (var fuseH = Mockito.mockStatic(fuse_2_h.class);
			 var scope = MemorySession.openConfined()) {
			fuseH.when(() -> fuse_2_h.fuse_parse_cmdline(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).then(invocation -> {
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
	@DisplayName("utimens")
	public class Utimens {

		@DisplayName("utimens(\"/foo\", UTIME_NOW, UTIME_NOW)")
		@Test
		public void testUtimensNow() {
			try (var scope = MemorySession.openConfined()) {
				var path = scope.allocateUtf8String("/foo");
				var times = MemoryAddress.NULL;
				var fi = scope.allocate(fuse3_file_info.$LAYOUT());
				Mockito.doReturn(42).when(fuseOps).utimens(Mockito.eq("/foo"), Mockito.argThat(t -> t.get().getNano() == 0L), Mockito.argThat(t -> t.get().getNano() == 0L), Mockito.argThat(usesSameMemorySegement(fi)));

				var result = fuseImpl.utimens(path.address(), times, fi.address());

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
	}


	@Nested
	@DisplayName("getattr")
	public class GetAttr {

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
		@DisplayName("fgetattr")
		public void testFgetattr() {
			try (var scope = MemorySession.openConfined()) {
				var path = scope.allocateUtf8String("/foo");
				var attr = fuse_stat.allocate(scope);
				var fi = fuse3_file_info.allocate(scope);
				Mockito.doReturn(42).when(fuseOps).getattr(Mockito.eq("/foo"), Mockito.any(), Mockito.argThat(usesSameMemorySegement(fi)));

				var result = fuseImpl.fgetattr(path.address(), attr.address(), fi.address());

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
			try (var scope = MemorySession.openConfined()) {
				var path = scope.allocateUtf8String("/foo");
				var fi = scope.allocate(fuse3_file_info.$LAYOUT());
				Mockito.doReturn(42).when(fuseOps).truncate(Mockito.eq("/foo"), Mockito.eq(1337L), Mockito.argThat(usesSameMemorySegement(fi)));

				var result = fuseImpl.truncate(path.address(), 1337L, fi.address());

				Assertions.assertEquals(42, result);
			}
		}

		@Test
		@DisplayName("ftruncate")
		public void testFtruncate() {
			try (var scope = MemorySession.openConfined()) {
				var path = scope.allocateUtf8String("/foo");
				var fi = fuse3_file_info.allocate(scope);
				Mockito.doReturn(42).when(fuseOps).truncate(Mockito.eq("/foo"), Mockito.eq(1337L), Mockito.argThat(usesSameMemorySegement(fi)));

				var result = fuseImpl.ftruncate(path.address(), 1337L, fi.address());

				Assertions.assertEquals(42, result);
			}
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