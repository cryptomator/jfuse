package de.skymatic.fusepanama.linux;

import de.skymatic.fusepanama.Fuse;
import de.skymatic.fusepanama.FuseOperations;
import de.skymatic.fusepanama.linux.lowlevel.fuse_h;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;

import java.util.concurrent.CompletableFuture;

public final class LinuxFuse extends Fuse {

	private final LinuxFuseOperationsMapper fuseOperations;

	public LinuxFuse(FuseOperations fuseOperations) {
		this.fuseOperations = new LinuxFuseOperationsMapper(fuseOperations, fuseScope);
	}

	@Override
	protected CompletableFuture<Integer> initialized() {
		return fuseOperations.initialized;
	}

	@Override
	protected CompletableFuture<Void> destroyed() {
		return fuseOperations.destroyed;
	}

	@Override
	protected int fuseMain(int argc, MemorySegment argv) {
		return fuse_h.fuse_main_real(argc, argv, fuseOperations.struct, fuseOperations.struct.byteSize(), MemoryAddress.NULL);
	}

}
