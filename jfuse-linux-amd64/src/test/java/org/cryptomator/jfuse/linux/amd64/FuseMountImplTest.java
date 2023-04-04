package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.linux.amd64.extr.fuse_h;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.foreign.MemorySegment;

public class FuseMountImplTest {

	private MockedStatic<fuse_h> fuseH;
	private FuseArgs fuseArgs = Mockito.mock(FuseArgs.class);
	private MemorySegment fuse = MemorySegment.ofAddress(42L);
	private FuseMountImpl fuseMount = new FuseMountImpl(fuse, fuseArgs);

	@BeforeEach
	public void setup() {
		this.fuseH = Mockito.mockStatic(fuse_h.class);
		Mockito.doReturn(1).when(fuseArgs).cloneFd();
		Mockito.doReturn(2).when(fuseArgs).maxIdleThreads();
		Mockito.doReturn(3).when(fuseArgs).maxThreads();
	}

	@AfterEach
	public void teardown() {
		this.fuseH.close();
	}

	@Test
	@DisplayName("mounting singlethreaded calls fuse_loop")
	public void testLoopSingleThreaded() {
		Mockito.doReturn(false).when(fuseArgs).multithreaded();

		fuseMount.loop();

		fuseH.verify(() -> fuse_h.fuse_loop(fuse));
		fuseH.verify(() -> fuse_h.fuse_loop_mt(Mockito.any(), Mockito.any()), Mockito.never());
	}

	@Test
	@DisplayName("FUSE 3.1 calls fuse_loop")
	public void testLoopMultiThreaded31() {
		Mockito.doReturn(true).when(fuseArgs).multithreaded();
		fuseH.when(fuse_h::fuse_version).thenReturn(31);

		fuseMount.loop();

		fuseH.verify(() -> fuse_h.fuse_loop(fuse));
		fuseH.verify(() -> fuse_h.fuse_loop_mt(Mockito.any(), Mockito.any()), Mockito.never());
	}

	@ParameterizedTest(name = "fuse_version() = {0}")
	@ValueSource(ints = {32, 35, 310, 311})
	@DisplayName("FUSE 3.2 - 3.11 calls fuse_loop_mt")
	public void testLoopMultiThreaded32(int fuseVersion) {
		Mockito.doReturn(true).when(fuseArgs).multithreaded();
		fuseH.when(fuse_h::fuse_version).thenReturn(fuseVersion);

		fuseMount.loop();

		fuseH.verify(() -> fuse_h.fuse_loop_mt(Mockito.eq(fuse), Mockito.any()));
		fuseH.verify(() -> fuse_h.fuse_loop(Mockito.any()), Mockito.never());
	}

	@Test
	@DisplayName("FUSE 3.12 calls fuse_loop_mt")
	public void testLoopMultiThreaded312() {
		var loopCfg = MemorySegment.ofAddress(1337L);
		Mockito.doReturn(true).when(fuseArgs).multithreaded();
		fuseH.when(fuse_h::fuse_version).thenReturn(312);
		fuseH.when(fuse_h::fuse_loop_cfg_create).thenReturn(loopCfg);

		fuseMount.loop();

		fuseH.verify(() -> fuse_h.fuse_loop_cfg_set_clone_fd(loopCfg, 1));
		fuseH.verify(() -> fuse_h.fuse_loop_cfg_set_max_threads(loopCfg, 3));
		fuseH.verify(() -> fuse_h.fuse_loop_mt(fuse, loopCfg));
		fuseH.verify(() -> fuse_h.fuse_loop(Mockito.any()), Mockito.never());
		fuseH.verify(() -> fuse_h.fuse_loop_cfg_destroy(loopCfg));
	}

}