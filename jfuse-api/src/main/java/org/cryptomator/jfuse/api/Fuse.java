package org.cryptomator.jfuse.api;


import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.MustBeInvokedByOverriders;

import org.jetbrains.annotations.BlockingExecutor;

import java.lang.foreign.MemorySession;
import java.lang.foreign.ValueLayout;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;

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
	private final AtomicReference<FuseSession> session = new AtomicReference<>();
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
		final FuseSession lock = new FuseSession(null, null);
		if (!session.compareAndSet(null, lock)) {
			throw new IllegalStateException("Already mounted");
		}

		List<String> args = new ArrayList<>();
		args.add(progName);
		args.addAll(List.of(flags));
		args.add("-f"); // always stay in foreground. don't fork & kill this process via `fuse_daemonize`
		args.add(mountPoint.toString());

		try (var scope = MemorySession.openConfined()) {
			var fuseArgs = parseCmdLine(args, scope);
			var fuseSession = mount(fuseArgs, mountPoint); // TODO: specific exception
			var isOnlySession = session.compareAndSet(lock, fuseSession);
			assert isOnlySession : "unreachable code, as no other method can set this.session to SESSION_LOCK";

			// TODO get multithreaded flag from fuseSession
			var mountResult = CompletableFuture.supplyAsync(() -> loop(fuseSession, false), executor);
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
		} finally {
			session.compareAndSet(lock, null); // reset in case of error
		}
	}

	/**
	 * Invokes {@code fuse_parse_cmdline}.
	 *
	 * @param args  fuse flags
	 * @param scope memory session that the returned result is bound to
	 * @return Parsed flags ready to use for mounting
	 * @throws IllegalArgumentException If {@code fuse_parse_cmdline} returns -1.
	 */
	protected abstract FuseArgs parseCmdLine(List<String> args, MemorySession scope) throws IllegalArgumentException;

	/**
	 * Invokes {@code fuse_mount} and {@code fuse_new} to mount a new fuse file system.
	 *
	 * @param args       The mount args
	 * @param mountPoint The mount point
	 * @return A new fuse session
	 */
	protected abstract FuseSession mount(FuseArgs args, Path mountPoint);
//	@Blocking
//	protected int fuseMain(List<String> flags) {
//		try (var scope = MemorySession.openConfined()) {
//			//var allocator =  SegmentAllocator.nativeAllocator(scope);
//			var argc = flags.size();
//			var argv = scope.allocateArray(ValueLayout.ADDRESS, argc);
//			for (int i = 0; i < argc; i++) {
//				var cString = scope.allocateUtf8String(flags.get(i));
//				argv.setAtIndex(ValueLayout.ADDRESS, i, cString);
//			}
//			return fuseMain(argc, argv);
//		}
//	}

	/**
	 * Invokes {@code fuse_loop} or {@code fuse_loop_mt}, depending on {@code multithreaded}.
	 *
	 * @param session       The fuse session
	 * @param multithreaded multi-threaded mode
	 * @return result passed through by {@code fuse_loop} or {@code fuse_loop_mt}
	 */
	@Blocking
	protected abstract int loop(FuseSession session, boolean multithreaded);

	/**
	 * Unmounts the file system and exits any running loops.
	 * <p>
	 * The implementation needs to be idempotent, i.e. repeated invokations must be no-ops.
	 *
	 * @param session The fuse session
	 */
	protected abstract void unmount(FuseSession session);

	/**
	 * Invokes {@code fuse_destroy}.
	 *
	 * @param session The fuse session
	 */
	protected abstract void destroy(FuseSession session);

	/**
	 * @return A future that completes with {@code 0} as soon as {@link FuseOperations#init(FuseConnInfo) init} has been called.
	 */
	protected abstract CompletableFuture<Integer> initialized();

	/**
	 * Unmounts (if needed) this fuse file system and frees up system resources.
	 * <p>
	 * <strong>Important:</strong> Before closing, a graceful unmount via system tools (e.g. {@code fusermount -u}) should be attempted.
	 */
	@Override
	@MustBeInvokedByOverriders
	public void close() throws TimeoutException {
		try {
			var session = this.session.getAndSet(null);
			if (session != null) {
				unmount(session);
				executor.shutdown();
				boolean exited = executor.awaitTermination(10, TimeUnit.SECONDS);
				if (!exited) {
					throw new TimeoutException("fuse main loop continued runn");
				}
				destroy(session);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			fuseScope.close();
		}
	}

}
