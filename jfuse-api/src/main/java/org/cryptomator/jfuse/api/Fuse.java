package org.cryptomator.jfuse.api;


import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

import java.io.IOException;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.lang.foreign.SegmentAllocator;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;


/**
 * Represents a FUSE file system. Instances of this class are stateful and can not be reused.
 * <p>
 * The lifecycle starts by creating an instance using the {@link #builder() builder},
 * then {@link #mount(String, Path, String...) mounting} the file system and {@link #close() closing} it when done.
 */
public abstract class Fuse implements AutoCloseable {

	private static final String MOUNT_PROBE = "/jfuse_mount_probe"; // Used to check, if the mounted fs is actually accessible, see https://github.com/winfsp/winfsp/discussions/440
	private static final FuseMount UNMOUNTED = new UnmountedFuseMount();
	private static final ThreadFactory THREAD_FACTORY = Thread.ofPlatform().name("jfuse-main-", 0).daemon().factory();

	/**
	 * The memory session associated with the lifecycle of this Fuse instance.
	 */
	protected final MemorySession fuseScope = MemorySession.openShared();

	/**
	 * The file system operations invoked by this FUSE file system.
	 */
	protected final FuseOperations fuseOperations;

	/**
	 * The memory segment containing the fuse_operations struct.
	 */
	protected final MemorySegment fuseOperationsStruct;

	private final AtomicReference<FuseMount> mount = new AtomicReference<>(UNMOUNTED);
	private final ExecutorService executor = Executors.newSingleThreadExecutor(THREAD_FACTORY);
	private final CountDownLatch mountProbeSucceeded = new CountDownLatch(1);

	/**
	 * Creates a new FUSE session.
	 *
	 * @param fuseOperations The file system operations
	 */
	protected Fuse(FuseOperations fuseOperations, Function<SegmentAllocator, MemorySegment> structAllocator) {
		this.fuseOperations = new MountProbeObserver(fuseOperations, mountProbeSucceeded::countDown);
		this.fuseOperationsStruct = structAllocator.apply(fuseScope);
		fuseOperations.supportedOperations().forEach(this::bind);
	}

	/**
	 * Gets the builder suitable for the current platform.
	 *
	 * @return A FuseBuilder
	 */
	public static FuseBuilder builder() {
		return FuseBuilder.getSupported();
	}

	/**
	 * Registers the callback function for the given operation in the {@code fuse_operations} struct.
	 * <p>
	 * Implementers need to make sure to:
	 * <ol>
	 *     <li>create an upcall stub for the given operation and save its address at the appropriate position within the
	 *     {@link #fuseOperationsStruct}</li>
	 *     <li>the necessary adaption between native and high-level Java types takes place</li>
	 *     <li>the adapter calls the corresponding function in {@link #fuseOperations}</li>
	 * </ol>
	 *
	 * @param operation Which function
	 */
	protected abstract void bind(FuseOperations.Operation operation);

	/**
	 * Mounts this fuse file system at the given mount point.
	 * <p>
	 * This method blocks until either {@link FuseOperations#init(FuseConnInfo, FuseConfig)} completes or an error occurs.
	 *
	 * @param progName   The program name used to construct a usage message and to derive a fallback for <code>-ofsname=...</code>
	 * @param mountPoint mount point
	 * @param flags      Additional flags. Use flag <code>-help</code> to get a list of available flags
	 * @throws FuseMountFailedException If mounting failed
	 * @throws IllegalArgumentException If providing unsupported mount flags
	 */
	@Blocking
	@MustBeInvokedByOverriders
	public void mount(String progName, Path mountPoint, String... flags) throws FuseMountFailedException, IllegalArgumentException {
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
			executor.submit(() -> fuseLoop(fuseMount)); // TODO keep reference of future and report result
			waitForMountingToComplete(mountPoint);
			mount.compareAndSet(lock, fuseMount);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new FuseMountFailedException("Interrupted while waiting for mounting to finish");
		} finally {
			mount.compareAndSet(lock, UNMOUNTED); // if value is still `lock`, mount has failed.
		}
	}

	@VisibleForTesting
	void waitForMountingToComplete(Path mountPoint) throws InterruptedException {
		var probe = Files.getFileAttributeView(mountPoint.resolve(MOUNT_PROBE.substring(1)), BasicFileAttributeView.class);
		do {
			try {
				probe.readAttributes(); // we don't care about the result, we just want to trigger a getattr call
			} catch (IOException e) {
				// noop
			}
		} while (!mountProbeSucceeded.await(200, TimeUnit.MILLISECONDS));
	}

	@Blocking
	private int fuseLoop(FuseMount mount) {
		AtomicInteger result = new AtomicInteger();
		fuseScope.whileAlive(() -> {
			result.set(mount.loop());
		});
		return result.get();
	}

	/**
	 * Mounts the fuse file system.
	 *
	 * @param args Mount args
	 * @return A mount object
	 * @throws FuseMountFailedException Thrown if mounting failed
	 * @throws IllegalArgumentException Thrown if parsing {@code args} failed
	 */
	@Blocking
	protected abstract FuseMount mount(List<String> args) throws FuseMountFailedException, IllegalArgumentException;

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

	/**
	 * Decorates the {@link FuseOperations#getattr(String, Stat, FileInfo) getattr} call of a FuseOperations object
	 * in order to detect accesses to {@value MOUNT_PROBE} system during {@link #waitForMountingToComplete(Path)}.
	 *
	 * @param delegate  The original FuseOperations object
	 * @param onObserve Handler to invoke as soon as the desired call is detected
	 */
	private record MountProbeObserver(FuseOperations delegate, Runnable onObserve) implements FuseOperationsDecorator {

		@Override
		public int getattr(String path, Stat stat, @Nullable FileInfo fi) {
			if (MOUNT_PROBE.equals(path)) {
				onObserve.run();
			}
			return delegate.getattr(path, stat, fi);
		}
	}

}
