package org.cryptomator.jfuse.api;

import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.BlockingExecutor;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.lang.foreign.ValueLayout;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Represents a FUSE file system. Instances of this class are stateful and can not be reused.
 * <p>
 * The lifecycle starts by creating an instance using the {@link #builder() builder},
 * then {@link #mount(String, Path, String...) mounting} the file system and {@link #close() closing} it when done.
 */
public abstract class Fuse implements AutoCloseable {

	protected final MemorySession fuseScope = MemorySession.openShared();
	@BlockingExecutor
	private final ExecutorService executor;

	protected Fuse() {
		this.executor = Executors.newSingleThreadExecutor(runnable -> {
			var thread = new Thread(runnable);
			thread.setName("jfuse-main"); // TODO append id
			thread.setDaemon(true);
			return thread;
		});
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
	public int mount(String progName, Path mountPoint, String... flags) throws CompletionException, TimeoutException {
		List<String> args = new ArrayList<>();
		args.add(progName);
		args.add("-f"); // foreground mode required, so fuse_main_real() blocks
		args.add(mountPoint.toString());
		args.addAll(List.of(flags));
		return mount(args);
	}

	protected int mount(List<String> args) throws TimeoutException {
		// keep reference in field and cancel during close()?
		var mountResult = CompletableFuture.supplyAsync(() -> fuseMain(args), executor);
		try {
			// fuseMain() will block (unless failing with return code != 0), therefore we need to wait for init()
			// if any of these two completes, we know that mounting succeeded of failed.
			var result = CompletableFuture.anyOf(mountResult, initialized()).get(10, TimeUnit.SECONDS);
			if (result instanceof Integer i) {
				return i;
			} else {
				throw new IllegalStateException("Expected Future<Integer>");
			}
		} catch (CancellationException | ExecutionException e) {
			return 1;
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return 1;
		}
	}

	@Blocking
	protected int fuseMain(List<String> flags) {
		try (var scope = MemorySession.openConfined()) {
			//var allocator =  SegmentAllocator.nativeAllocator(scope);
			var argc = flags.size();
			var argv = scope.allocateArray(ValueLayout.ADDRESS, argc);
			for (int i = 0; i < argc; i++) {
				var cString = scope.allocateUtf8String(flags.get(i));
				argv.setAtIndex(ValueLayout.ADDRESS, i, cString);
			}
			return fuseMain(argc, argv);
		}
	}

	@Blocking
	protected abstract int fuseMain(int argc, MemorySegment argv);

	protected abstract CompletableFuture<Integer> initialized();

	@Override
	public void close() throws TimeoutException {
		try {
			executor.shutdown();
			boolean exited = executor.awaitTermination(10, TimeUnit.SECONDS);
			if (!exited) {
				throw new TimeoutException("fuse main loop continued runn");
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			fuseScope.close();
		}
	}

}
