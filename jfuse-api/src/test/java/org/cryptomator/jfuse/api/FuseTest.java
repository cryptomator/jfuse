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

public class FuseTest {

	private FuseOperations fuseOps = Mockito.mock(FuseOperations.class);
	private Fuse fuse = Mockito.spy(new FuseStub(fuseOps));

	@Test
	@DisplayName("waitForMountingToComplete() waits for getattr(\"/jfuse_mount_probe\")")
	public void testWaitForMountingToComplete() throws IOException {
		Path mountPoint = Mockito.mock(Path.class, "/mnt");
		Path probePath = Mockito.mock(Path.class, "/mnt/jfuse_mount_probe");
		FileSystem fs = Mockito.mock(FileSystem.class);
		FileSystemProvider fsProv = Mockito.mock(FileSystemProvider.class);
		BasicFileAttributeView attrView = Mockito.mock(BasicFileAttributeView.class);
		Mockito.doReturn(probePath).when(mountPoint).resolve("jfuse_mount_probe");
		Mockito.doReturn(fs).when(probePath).getFileSystem();
		Mockito.doReturn(fsProv).when(fs).provider();
		Mockito.doReturn(attrView).when(fsProv).getFileAttributeView(probePath, BasicFileAttributeView.class);
		Mockito.doAnswer(invocation -> {
			// first attempt: not yet mounted
			throw new NoSuchFileException("/mnt/jfuse_mount_probe not found");
		}).doAnswer(invocation -> {
			// second attempt: simulate hitting getattr
			fuse.delegate.getattr("/jfuse_mount_probe", Mockito.mock(Stat.class), Mockito.mock(FileInfo.class));
			throw new NoSuchFileException("/mnt/jfuse_mount_probe still not found");
		}).when(attrView).readAttributes();

		Assertions.assertTimeoutPreemptively(Duration.ofSeconds(1), () -> fuse.waitForMountingToComplete(mountPoint));

		Mockito.verify(fuseOps).getattr(Mockito.eq("/jfuse_mount_probe"), Mockito.any(), Mockito.any());
	}

	private static class FuseStub extends Fuse {

		protected FuseStub(FuseOperations fuseOperations) {
			super(fuseOperations, allocator -> allocator.allocate(0L));
		}

		@Override
		protected void bind(FuseOperations.Operation operation) {
			// no-op
		}

		@Override
		protected FuseMount mount(List<String> args) {
			return new FuseMount() {

				@Override
				public int loop() {
					return 0;
				}

				@Override
				public void unmount() {
					// no-op
				}

				@Override
				public void destroy() {
					// no-op
				}
			};
		}
	}

}