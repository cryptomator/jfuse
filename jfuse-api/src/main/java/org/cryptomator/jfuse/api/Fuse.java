package org.cryptomator.jfuse.api;


import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.BlockingExecutor;
import org.jetbrains.annotations.MustBeInvokedByOverriders;

import java.lang.foreign.MemorySession;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
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
	 * This method blocks until either {@link FuseOperations#init(FuseConnInfo, FuseConfig)} completes or an error occurs.
	 *
	 * @param progName   The program name used to construct a usage message and to derive a fallback for <code>-ofsname=...</code>
	 * @param mountPoint mount point
	 * @param flags      Additional flags. Use flag <code>-help</code> to get a list of available flags
	 * @throws MountFailedException If mounting failed
	 * @throws IllegalArgumentException If providing unsupported mount flags
	 */
	@Blocking
	@MustBeInvokedByOverriders
	public void mount(String progName, Path mountPoint, String... flags) throws MountFailedException, IllegalArgumentException {
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
			executor.submit(this::fuseLoop); // TODO keep reference of future and report result
		} finally {
			mount.compareAndSet(lock, UNMOUNTED); // if value is still `lock`, mount has failed.
		}
	}

	@Blocking
	private int fuseLoop() {
		AtomicInteger result = new AtomicInteger();
		fuseScope.whileAlive(() -> {
			var mount = this.mount.get();
			result.set(mount.loop());
		});
		return result.get();
	}

	@Blocking
	protected abstract FuseMount mount(List<String> args) throws MountFailedException, IllegalArgumentException;

	/**
	 * Unmounts (if needed) this fuse file system and frees up system resources.
	 * <p>
	 * <strong>Important:</strong> Before closing, a graceful unmount via system tools (e.g. {@code fusermount -u}) should be attempted.
	 */
	@Override
	@Blocking
	@MustBeInvokedByOverriders
	public void close() throws TimeoutException {
		try {
			var fuseMount = this.mount.getAndSet(UNMOUNTED);
			fuseMount.unmount();
			executor.shutdown();
			boolean exited = executor.awaitTermination(10, TimeUnit.SECONDS);
			if (!exited) {
				throw new TimeoutException("fuse main loop continued runn");
			}
			fuseMount.destroy();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			fuseScope.close();
		}
	}

}
