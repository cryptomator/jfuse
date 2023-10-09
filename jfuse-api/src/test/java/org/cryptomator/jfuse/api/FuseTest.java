package org.cryptomator.jfuse.api;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.spi.FileSystemProvider;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.Future;

public class FuseTest {

	private final FuseOperations fuseOps = Mockito.mock(FuseOperations.class);
	private final FuseMount fuseMount = Mockito.spy(new FuseMountStub());
	private final Fuse fuse = Mockito.spy(new FuseStub(fuseMount, fuseOps));
	private final Path mountPoint = Mockito.mock(Path.class, "/mnt");

	@Test
	@DisplayName("waitForMountingToComplete() waits for getattr(\"/jfuse_mount_probe\")")
	public void testWaitForMountingToComplete() throws IOException {
		Path probePath = Mockito.mock(Path.class, "/mnt/jfuse_mount_probe");
		FileSystem fs = Mockito.mock(FileSystem.class);
		FileSystemProvider fsProv = Mockito.mock(FileSystemProvider.class);
		BasicFileAttributeView attrView = Mockito.mock(BasicFileAttributeView.class);
		Mockito.doReturn(probePath).when(mountPoint).resolve("jfuse_mount_probe");
		Mockito.doReturn(fs).when(probePath).getFileSystem();
		Mockito.doReturn(fsProv).when(fs).provider();
		Mockito.doReturn(attrView).when(fsProv).getFileAttributeView(probePath, BasicFileAttributeView.class);
		Mockito.doAnswer(_ -> {
			// first attempt: not yet mounted
			throw new NoSuchFileException("/mnt/jfuse_mount_probe not found");
		}).doAnswer(_ -> {
			// second attempt: simulate hitting getattr
			fuse.fuseOperations.getattr("/jfuse_mount_probe", Mockito.mock(Stat.class), Mockito.mock(FileInfo.class));
			throw new NoSuchFileException("/mnt/jfuse_mount_probe still not found");
		}).when(attrView).readAttributes();
		Future<Integer> fuseLoop = Mockito.mock(Future.class);
		Mockito.doReturn(false).when(fuseLoop).isDone();

		Assertions.assertTimeoutPreemptively(Duration.ofSeconds(1), () -> fuse.waitForMountingToComplete(mountPoint, fuseLoop));
		Mockito.verify(fuseLoop, Mockito.atLeastOnce()).isDone();
	}

	@Test
	@DisplayName("waitForMountingToComplete() waits returns immediately if fuse_loop fails")
	public void testPrematurelyFuseLoopReturn() {
		Path probePath = Mockito.mock(Path.class, "/mnt/jfuse_mount_probe");
		FileSystem fs = Mockito.mock(FileSystem.class);
		FileSystemProvider fsProv = Mockito.mock(FileSystemProvider.class);
		BasicFileAttributeView attrView = Mockito.mock(BasicFileAttributeView.class);
		Mockito.doReturn(probePath).when(mountPoint).resolve("jfuse_mount_probe");
		Mockito.doReturn(fs).when(probePath).getFileSystem();
		Mockito.doReturn(fsProv).when(fs).provider();
		Mockito.doReturn(attrView).when(fsProv).getFileAttributeView(probePath, BasicFileAttributeView.class);
		Future<Integer> fuseLoop = Mockito.mock(Future.class);
		Mockito.doReturn(true).when(fuseLoop).isDone();

		Assertions.assertTimeoutPreemptively(Duration.ofSeconds(1), () -> fuse.waitForMountingToComplete(mountPoint, fuseLoop));
		Mockito.verify(fuseLoop, Mockito.atLeastOnce()).isDone();
	}

	@Test
	@DisplayName("Already closed fuseMount throws IllegalStateException on mount")
	public void testMountThrowsIllegalStateIfClosed() {
		Assertions.assertDoesNotThrow(fuse::close);
		Assertions.assertThrows(IllegalStateException.class, () -> fuse.mount("test3000", mountPoint));
	}

	@Test
	@DisplayName("Already mounted fuseMount throws IllegalStateException on mount")
	public void testMountThrowsIllegalStateIfAlreadyMounted() throws InterruptedException {
		// mount probe succeeds immediately...
		Mockito.doNothing().when(fuse).waitForMountingToComplete(Mockito.eq(mountPoint), Mockito.any());
		// ... before fuse_loop returns
		Mockito.doAnswer(_ -> {
			Thread.sleep(1000);
			return 0;
		}).when(fuseMount).loop();
		Assertions.assertDoesNotThrow(() -> fuse.mount("test3000", mountPoint));
		Assertions.assertThrows(IllegalStateException.class, () -> fuse.mount("test3000", mountPoint));
	}

	@Test
	@DisplayName("If fuse_loop instantly returns with non-zero result, throw FuseMountFailedException")
	public void testMountThrowsFuseMountFailedIfLoopReturnsNonZero() throws InterruptedException {
		// mount probe takes a while...
		Mockito.doAnswer(_ -> {
			Thread.sleep(1000);
			return null;
		}).when(fuse).waitForMountingToComplete(Mockito.eq(mountPoint), Mockito.any());
		// ... but fuse_loop returns immediately (with error)
		Mockito.doReturn(1).when(fuseMount).loop();
		Assertions.assertThrows(FuseMountFailedException.class, () -> fuse.mount("test3000", mountPoint));
	}

	private static class FuseStub extends Fuse {

		FuseMount fuseMount;

		protected FuseStub(FuseMount mountStub, FuseOperations fuseOperations) {
			super(fuseOperations, allocator -> allocator.allocate(0L));
			this.fuseMount = mountStub;
		}

		@Override
		protected void bind(FuseOperations.Operation operation) {
			// no-op
		}

		@Override
		protected FuseMount mount(List<String> args) {
			return fuseMount;
		}
	}

	private record FuseMountStub() implements FuseMount {

		@Override
		public int loop() {
			return 0;
		}

		@Override
		public void unmount() {
		}

		@Override
		public void destroy() {
		}
	}

}