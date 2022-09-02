package org.cryptomator.jfuse.api;


import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.BlockingExecutor;
import org.jetbrains.annotations.MustBeInvokedByOverriders;

import java.lang.foreign.MemorySession;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;


/**
 * Represents a FUSE file system. Instances of this class are stateful and can not be reused.
 * <p>
 * The lifecycle starts by creating an instance using the {@link #builder() builder},
 * then {@link #mount(String, Path, String...) mounting} the file system and {@link #close() closing} it when done.
 */
public abstract class Fuse implements AutoCloseable {

	private static final FuseMount UNMOUNTED = new UnmountedFuseMount();

	protected final MemorySession fuseScope = MemorySession.openShared();
	private final AtomicReference<FuseMount> mount = new AtomicReference<>(UNMOUNTED);
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
	@Blocking
	public int mount(String progName, Path mountPoint, String... flags) throws CompletionException, TimeoutException {
		FuseMount lock = new UnmountedFuseMount();
		if (!mount.compareAndSet(UNMOUNTED, lock)) {
			throw new IllegalStateException("Already mounted");
		}

		List<String> args = new ArrayList<>();
		args.add(progName);
		args.addAll(List.of(flags));
		args.add("-f"); // always stay in foreground. don't fork & kill this process via `fuse_daemonize`
		args.add(mountPoint.toString());

		try {
			var fuseMount = this.mount(args);
			var isOnlySession = mount.compareAndSet(lock, fuseMount);
			assert isOnlySession : "unreachable code, as no other method can set this.mount to lock";
			executor.submit(fuseMount::loop); // TODO keep reference of future?
		} finally {
			mount.compareAndSet(lock, UNMOUNTED); // if value is still `lock`, mount has failed.
		}
		return 0; // TODO make this void and use proper exceptions
	}

	protected abstract FuseMount mount(List<String> args);

	/**
	 * Unmounts (if needed) this fuse file system and frees up system resources.
	 * <p>
	 * <strong>Important:</strong> Before closing, a graceful unmount via system tools (e.g. {@code fusermount -u}) should be attempted.
	 */
	@Override
	@MustBeInvokedByOverriders
	public void close() throws TimeoutException {
		try {
			var mount = this.mount.getAndSet(null);
			if (mount != null) {
				mount.unmount();
				executor.shutdown();
				boolean exited = executor.awaitTermination(10, TimeUnit.SECONDS);
				if (!exited) {
					throw new TimeoutException("fuse main loop continued runn");
				}
				mount.destroy();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			fuseScope.close();
		}
	}

}
