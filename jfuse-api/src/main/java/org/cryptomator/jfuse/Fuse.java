package org.cryptomator.jfuse;

import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.foreign.SegmentAllocator;
import jdk.incubator.foreign.ValueLayout;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CountDownLatch;

public abstract class Fuse implements AutoCloseable {

	protected final ResourceScope fuseScope = ResourceScope.newSharedScope();
	private final CountDownLatch mainExited = new CountDownLatch(1);

	protected Fuse() {
	}

	public static FuseBuilder builder() {
		return FuseBuilder.getSupported();
	}

	/**
	 * Mounts this fuse file system at the given mount point.
	 * <p>
	 * This method blocks until either {@link FuseOperations#init(FuseConnInfo)} completes or an error occurs.
	 *
	 * @param progName   The program name used to construct a usage message and to derive a fallback for <code>-ofsname=...</code>
	 * @param mountPoint mount point
	 * @param flags      Additional flags. Use flag <code>-help</code> to get a list of available flags
	 * @return 0 if mounted successfully, or the result of <code>fuse_main_real()</code> in case of errors
	 * @throws CompletionException wrapping exceptions thrown during <code>init()</code> or <code>fuse_main_real()</code>
	 */
	public int mount(String progName, Path mountPoint, String... flags) throws CompletionException {
		List<String> args = new ArrayList<>();
		args.add(progName);
		args.addAll(List.of(flags));
		args.add("-f"); // foreground mode required, so fuse_main_real() blocks
		args.add(mountPoint.toString());
		return mount(args);
	}

	protected int mount(List<String> args) throws CompletionException {
		// fuseMain() will block (unless failing with return code != 0), therefore we need to wait for init()
		// if any of these two completes, we know that mounting succeeded of failed.
		var mountResult = CompletableFuture.supplyAsync(() -> fuseMain(args));
		var result = CompletableFuture.anyOf(mountResult, initialized()).join();
		if (result instanceof Integer i) {
			return i;
		} else {
			throw new IllegalStateException("Expected Future<Integer>");
		}
	}

	protected int fuseMain(List<String> flags) {
		try (var scope = ResourceScope.newConfinedScope()) {
			var allocator = SegmentAllocator.nativeAllocator(scope);
			var argc = flags.size();
			var argv = allocator.allocateArray(ValueLayout.ADDRESS, argc);
			for (int i = 0; i < argc; i++) {
				var cString = allocator.allocateUtf8String(flags.get(i));
				argv.setAtIndex(ValueLayout.ADDRESS, i, cString);
			}
			return fuseMain(argc, argv);
		} finally {
			mainExited.countDown(); // make sure fuse_main finished before allowing to release fuseScope
		}
	}

	protected abstract int fuseMain(int argc, MemorySegment argv);

	protected abstract CompletableFuture<Integer> initialized();

	@Override
	public void close() {
		try {
			mainExited.await(); // TODO: check if main has been started first?
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			fuseScope.close();
		}
	}

}
