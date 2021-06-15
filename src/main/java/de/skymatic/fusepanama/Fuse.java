package de.skymatic.fusepanama;

import de.skymatic.fusepanama.lowlevel.fuse_context;
import de.skymatic.fusepanama.lowlevel.fuse_h;
import de.skymatic.fusepanama.lowlevel.fuse_operations;
import jdk.incubator.foreign.Addressable;
import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.foreign.SegmentAllocator;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Fuse implements AutoCloseable {

	private final CompletableFuture<Integer> initResult = new CompletableFuture<>();
	private final CompletableFuture<Void> destroyResult = new CompletableFuture<>();
	private final ResourceScope fuseScope = ResourceScope.newSharedScope();
	private final FuseOperations userFuseOperations;
	private final MemorySegment nativeFuseOperations;

	public Fuse(FuseOperations fuseOperations) {
		this.userFuseOperations = fuseOperations;
		this.nativeFuseOperations = fuse_operations.allocate(fuseScope);
		fuseOperations.supportedOperations().forEach(op -> op.bind(fuseOperations, nativeFuseOperations, fuseScope));
		fuse_operations.init$set(nativeFuseOperations, fuse_operations.init.allocate(this::init, fuseScope));
		fuse_operations.destroy$set(nativeFuseOperations, fuse_operations.destroy.allocate(this::destroy, fuseScope));
	}

	/**
	 * Mounts this fuse file system at the given mount point.
	 * <p>
	 * This method blocks until either {@link FuseOperations#init(FuseConnInfo)} completes or an error occurs.
	 *
	 * @param mountPoint mount point
	 * @return 0 if mounted successfully, or the result of <code>fuse_main_real()</code> in case of errors
	 * @throws CompletionException wrapping exceptions thrown during <code>init()</code> or <code>fuse_main_real()</code>
	 */
	public int mount(Path mountPoint) throws CompletionException {
		// fuseMain() will block (unless failing with return code != 0), therefore we need to wait for init()
		// if any of these two completes, we know that mounting succeeded of failed.
		var mountResult = CompletableFuture.supplyAsync(() -> fuseMain(Arrays.asList("fusefs-3000", "-f", mountPoint.toString())));
		var result = CompletableFuture.anyOf(mountResult, initResult).join();
		if (result instanceof Integer i) {
			return i;
		} else {
			throw new IllegalStateException("Expected Future<Integer>");
		}
	}

	private int fuseMain(List<String> flags) {
		try (var scope = ResourceScope.newConfinedScope()) {
			var cStrings = flags.stream().map(s -> CLinker.toCString(s, UTF_8, scope)).toArray(Addressable[]::new);
			var allocator = SegmentAllocator.ofScope(scope);
			var argc = cStrings.length;
			var argv = allocator.allocateArray(CLinker.C_POINTER, cStrings);
			return fuse_h.fuse_main_real(argc, argv, nativeFuseOperations, nativeFuseOperations.byteSize(), MemoryAddress.NULL);
		}
	}

	private MemoryAddress init(MemoryAddress conn) {
		try (var scope = ResourceScope.newConfinedScope()) {
			if (userFuseOperations.supportedOperations().contains(FuseOperations.Operation.INIT)) {
				userFuseOperations.init(new FuseConnInfo(conn, scope));
			}
			initResult.complete(0);
		} catch (Exception e) {
			initResult.completeExceptionally(e);
		}
		return MemoryAddress.NULL;
	}

	private void destroy(MemoryAddress addr) {
		try {
			if (userFuseOperations.supportedOperations().contains(FuseOperations.Operation.DESTROY)) {
				userFuseOperations.destroy();
			}
			destroyResult.complete(null);
		} catch (Exception e) {
			destroyResult.completeExceptionally(e);
		}
	}

	@Override
	public void close() {
		try {
			if (Platform.IS_WINDOWS) {
				exit();
			} else {
				awaitUnmount();
			}
		} finally {
			fuseScope.close();
		}
	}

	private void awaitUnmount() {
		if (initResult.isDone()) {
			destroyResult.join();
		}
	}

	private void exit() {
		try (var scope = ResourceScope.newConfinedScope()) {
			var ctx = fuse_context.ofAddress(fuse_h.fuse_get_context(), scope);
			var fusePtr = fuse_context.fuse$get(ctx);
			fuse_h.fuse_exit(fusePtr);
		}
	}

}
