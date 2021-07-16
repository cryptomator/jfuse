package de.skymatic.fusepanama.mac;

import de.skymatic.fusepanama.Fuse;
import de.skymatic.fusepanama.FuseOperations;
import de.skymatic.fusepanama.mac.lowlevel.fuse_h;
import jdk.incubator.foreign.Addressable;
import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.foreign.SegmentAllocator;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class MacFuse implements Fuse {

	private final ResourceScope fuseScope = ResourceScope.newSharedScope();
	private final MacFuseOperationsMapper fuseOperations;

	public MacFuse(FuseOperations fuseOperations) {
		this.fuseOperations = new MacFuseOperationsMapper(fuseOperations, fuseScope);
	}

	@Override
	public int mount(Path mountPoint) throws CompletionException {
		// fuseMain() will block (unless failing with return code != 0), therefore we need to wait for init()
		// if any of these two completes, we know that mounting succeeded of failed.
		var mountResult = CompletableFuture.supplyAsync(() -> fuseMain(Arrays.asList("fusefs-3000", "-f", mountPoint.toString())));
		var result = CompletableFuture.anyOf(mountResult, fuseOperations.initialized).join();
		if (result instanceof Integer i) {
			return i;
		} else {
			throw new IllegalStateException("Expected Future<Integer>");
		}
	}

	private int fuseMain(List<String> flags) {
		try (var scope = ResourceScope.newConfinedScope()) {
			var cStrings = flags.stream().map(s -> CLinker.toCString(s, scope)).toArray(Addressable[]::new);
			var allocator = SegmentAllocator.ofScope(scope);
			var argc = cStrings.length;
			var argv = allocator.allocateArray(CLinker.C_POINTER, cStrings);
			return fuse_h.fuse_main_real(argc, argv, fuseOperations.struct, fuseOperations.struct.byteSize(), MemoryAddress.NULL);
		}
	}

	@Override
	public void close() {
		try {
			awaitUnmount();
		} finally {
			fuseScope.close();
		}
	}

	private void awaitUnmount() {
		if (fuseOperations.initialized.isDone()) {
			fuseOperations.destroyed.join();
		}
	}

//	private void exit() {
//		try (var scope = ResourceScope.newConfinedScope()) {
//			var ctx = fuse_context.ofAddress(fuse_h.fuse_get_context(), scope);
//			var fusePtr = fuse_context.fuse$get(ctx);
//			fuse_h.fuse_exit(fusePtr);
//		}
//	}

}
