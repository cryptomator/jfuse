package de.skymatic.fusepanama.mac;

import de.skymatic.fusepanama.Fuse;
import de.skymatic.fusepanama.FuseOperations;
import de.skymatic.fusepanama.mac.lowlevel.fuse_h;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;

import java.util.concurrent.CompletableFuture;

public final class MacFuse extends Fuse {

	static {
		System.loadLibrary("fuse");
	}

	private final MacFuseOperationsMapper fuseOperations;

	public MacFuse(FuseOperations fuseOperations) {
		this.fuseOperations = new MacFuseOperationsMapper(fuseOperations, fuseScope);
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
