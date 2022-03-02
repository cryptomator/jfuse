package org.cryptomator.jfuse.api;

import jdk.incubator.foreign.ResourceScope;
import org.jetbrains.annotations.Blocking;
import org.jetbrains.annotations.MustBeInvokedByOverriders;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

public abstract class Fuse implements AutoCloseable {

	protected final ResourceScope fuseScope = ResourceScope.newSharedScope();
	private final CountDownLatch loopExited = new CountDownLatch(1);
	private final AtomicReference<FuseSession> session = new AtomicReference<>();

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
	@Blocking
	public int mount(String progName, Path mountPoint, String... flags) throws CompletionException {
		final FuseSession lock = new FuseSession(null, null, null);
		if (!session.compareAndSet(null, lock)) {
			throw new IllegalStateException("Already mounted");
		}

		List<String> args = new ArrayList<>();
		args.add(progName);
		args.addAll(List.of(flags));
		args.add("-f"); // always stay in foreground. don't fork & kill this process via `fuse_daemonize`
		args.add(mountPoint.toString());

		try (var scope = ResourceScope.newConfinedScope()) {
			var fuseArgs = parseCmdLine(args, scope);
			var fuseSession = mount(fuseArgs, mountPoint); // TODO: specific exception
			var isOnlySession = session.compareAndSet(lock, fuseSession);
			assert isOnlySession : "unreachable code, as no other method can set this.session to SESSION_LOCK";

			var mountResult = CompletableFuture
					.supplyAsync(() -> loop(fuseSession, false)) // TODO: specify executor?
					.whenComplete((result, error) -> loopExited.countDown());
			var result = CompletableFuture.anyOf(mountResult, initialized()).join();
			if (result instanceof Integer i) {
				return i;
			} else {
				throw new IllegalStateException("Expected Future<Integer>");
			}
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
	protected abstract FuseArgs parseCmdLine(List<String> args, ResourceScope scope) throws IllegalArgumentException;

	/**
	 * Invokes {@code fuse_mount} and {@code fuse_new} to mount a new fuse file system.
	 *
	 * @param args       The mount args
	 * @param mountPoint The mount point
	 * @return A new fuse session
	 */
	protected abstract FuseSession mount(FuseArgs args, Path mountPoint);

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
	public void close() {
		try {
			var session = this.session.getAndSet(null);
			if (session != null) {
				unmount(session);
				loopExited.await();
				destroy(session);
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			fuseScope.close();
		}
	}

}
