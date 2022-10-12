package org.cryptomator.jfuse.linux.aarch64;

import org.cryptomator.jfuse.api.FuseConnInfo;
import org.cryptomator.jfuse.api.FuseOperations;
import org.cryptomator.jfuse.api.MountFailedException;
import org.cryptomator.jfuse.api.TimeSpec;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse_cmdline_opts;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse_config;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse_conn_info;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse_file_info;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse_h;
import org.cryptomator.jfuse.linux.aarch64.extr.timespec;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Answers;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
			fuseH.when(() -> fuse_h.fuse_new(Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.any())).thenReturn(MemoryAddress.NULL);

			var thrown = Assertions.assertThrows(MountFailedException.class, () -> fuseImplSpy.mount(args));

			fuseH.verify(() -> fuse_h.fuse_mount(Mockito.any(), Mockito.any()), Mockito.never());
			Assertions.assertEquals("fuse_new failed", thrown.getMessage());
		}

		@Test
		@DisplayName("MountFailedException when fuse_mount fails")
		public void testFuseMountFails() {
			fuseH.when(() -> fuse_h.fuse_new(Mockito.any(), Mockito.any(), Mockito.anyLong(), Mockito.any())).thenReturn(MemoryAddress.ofLong(42L));
			fuseH.when(() -> fuse_h.fuse_mount(Mockito.any(), Mockito.any())).thenReturn(1);

			var thrown = Assertions.assertThrows(MountFailedException.class, () -> fuseImplSpy.mount(args));

			Assertions.assertEquals("fuse_mount failed", thrown.getMessage());
		}

	}

	@ParameterizedTest(name = "fusefs {0}")
	@DisplayName("parseArgs with -h/--help")
	@ValueSource(strings = {"--help", "-h"})
	public void testParseArgsHelp(String arg) {
		var args = List.of("fusefs", arg);
		try (var fuseFunctionsClass = Mockito.mockStatic(FuseFunctions.class);
			 var fuseH = Mockito.mockStatic(fuse_h.class)) {
			fuseFunctionsClass.when(() -> FuseFunctions.fuse_parse_cmdline(Mockito.any(), Mockito.any())).then(invocation -> {
				MemorySegment opts = invocation.getArgument(1);
				fuse_cmdline_opts.show_help$set(opts, 1);
				return 0;
			});
			fuseH.when(() -> fuse_h.fuse_lib_help(Mockito.any())).thenAnswer(Answers.RETURNS_DEFAULTS);

			Assertions.assertThrows(IllegalArgumentException.class, () -> fuseImpl.parseArgs(args));
			fuseH.verify(() -> fuse_h.fuse_lib_help(Mockito.any()));
		}
	}

	@Test
	@DisplayName("parseArgs")
	public void testParseArgs() {
		try (var fuseFunctionsClass = Mockito.mockStatic(FuseFunctions.class);
			 var scope = MemorySession.openConfined()) {
			fuseFunctionsClass.when(() -> FuseFunctions.fuse_parse_cmdline(Mockito.any(), Mockito.any())).then(invocation -> {
				MemorySegment opts = invocation.getArgument(1);
				fuse_cmdline_opts.singlethread$set(opts, 0);
				fuse_cmdline_opts.debug$set(opts, 1);
				fuse_cmdline_opts.mountpoint$set(opts, scope.allocateUtf8String("/mount/point").address());
				return 0;
			});

			var fuseArgs = fuseImpl.parseArgs(List.of("fusefs", "-foo", "-bar", "/mount/point"));

			Assertions.assertTrue(fuseArgs.multithreaded());
			Assertions.assertTrue(fuseArgs.toString().contains("arg[0] = fusefs"));
			Assertions.assertTrue(fuseArgs.toString().contains("arg[1] = -foo"));
			Assertions.assertTrue(fuseArgs.toString().contains("arg[2] = -bar"));
			Assertions.assertTrue(fuseArgs.toString().contains("singlethreaded = false"));
			Assertions.assertTrue(fuseArgs.toString().contains("debug = 1"));
			Assertions.assertTrue(fuseArgs.toString().contains("mountPoint = /mount/point"));
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
			var connInfo = fuse_conn_info.allocate(scope);
			var fuseConfig = fuse_config.allocate(scope);

			fuseImpl.init(connInfo.address(), fuseConfig.address());

			Assertions.assertEquals(FuseConnInfo.FUSE_CAP_READDIRPLUS, result.get() & FuseConnInfo.FUSE_CAP_READDIRPLUS);
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
				var fi = scope.allocate(fuse_file_info.$LAYOUT());
				var times = MemoryAddress.NULL;
				Mockito.doReturn(42).when(fuseOps).utimens(Mockito.eq("/foo"), Mockito.argThat(TimeSpec::isUtimeNow), Mockito.argThat(TimeSpec::isUtimeNow), Mockito.any());

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
				var fi = scope.allocate(fuse_file_info.$LAYOUT());
				var times = timespec.allocateArray(2, scope);
				timespec.tv_sec$set(times, 0, sec0);
				timespec.tv_nsec$set(times, 0, nsec0);
				timespec.tv_sec$set(times, 1, sec1);
				timespec.tv_nsec$set(times, 1, nsec1);
				Mockito.doReturn(42).when(fuseOps).utimens(Mockito.eq("/foo"), Mockito.argThat(t -> expectedATime.equals(t.get())), Mockito.argThat(t -> expectedMTime.equals(t.get())), Mockito.any());

				var result = fuseImpl.utimens(path.address(), times.address(), fi.address());

				Assertions.assertEquals(42, result);
			}
		}
	}

}