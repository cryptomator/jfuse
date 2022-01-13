package de.skymatic.fusepanama.win.amd64;

import de.skymatic.fusepanama.Fuse;
import de.skymatic.fusepanama.FuseOperations;
import de.skymatic.fusepanama.win.amd64.lowlevel.fuse_h;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;

import java.util.concurrent.CompletableFuture;

public final class FuseImpl extends Fuse {

	private final FuseOperationsMapper fuseOperations;

	public FuseImpl(FuseOperations fuseOperations) {
		this.fuseOperations = new FuseOperationsMapper(fuseOperations, fuseScope);
	}

	@Override
	protected CompletableFuture<Integer> initialized() {
		return fuseOperations.initialized;
	}

	@Override
	protected int fuseMain(int argc, MemorySegment argv) {
		return fuse_h.fuse_main_real(argc, argv, fuseOperations.struct, fuseOperations.struct.byteSize(), MemoryAddress.NULL);
	}

//	private void exit() {
//		try (var scope = ResourceScope.newConfinedScope()) {
//			var ctx = fuse_context.ofAddress(fuse_h.fuse_get_context(), scope);
//			var fusePtr = fuse_context.fuse$get(ctx);
//			fuse_h.fuse_exit(fusePtr);
//		}
//	}

}
