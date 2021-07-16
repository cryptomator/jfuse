package de.skymatic.fusepanama;

import de.skymatic.fusepanama.linux.LinuxFuse;
import de.skymatic.fusepanama.mac.MacFuse;
import jdk.incubator.foreign.Addressable;
import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.foreign.SegmentAllocator;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public abstract sealed class Fuse implements AutoCloseable permits MacFuse, LinuxFuse {

	protected final ResourceScope fuseScope = ResourceScope.newSharedScope();

	protected Fuse() {
	}

	public static Fuse create(FuseOperations fuseOperations) {
		return switch (Platform.CURRENT) {
			case MAC -> new MacFuse(fuseOperations);
			case LINUX -> new LinuxFuse(fuseOperations);
			default -> throw new UnsupportedOperationException("");
		};
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
			var cStrings = flags.stream().map(s -> CLinker.toCString(s, scope)).toArray(Addressable[]::new);
			var allocator = SegmentAllocator.ofScope(scope);
			var argc = cStrings.length;
			var argv = allocator.allocateArray(CLinker.C_POINTER, cStrings);
			return fuseMain(argc, argv);
		}
	}

	protected abstract int fuseMain(int argc, MemorySegment argv);

	protected abstract CompletableFuture<Integer> initialized();

	protected abstract CompletableFuture<Void> destroyed();

	@Override
	public void close() {
		try {
			awaitUnmount();
		} finally {
			fuseScope.close();
		}
	}

	private void awaitUnmount() {
		if (initialized().isDone()) {
			destroyed().join();
		}
	}

}
