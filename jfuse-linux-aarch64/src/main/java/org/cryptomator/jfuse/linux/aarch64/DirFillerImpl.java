package org.cryptomator.jfuse.linux.aarch64;

import org.cryptomator.jfuse.api.DirFiller;
import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse_fill_dir_t;
import org.cryptomator.jfuse.linux.aarch64.extr.fuse_h;
import org.cryptomator.jfuse.linux.aarch64.extr.stat;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySession;
import java.util.Set;
import java.util.function.Consumer;

record DirFillerImpl(MemoryAddress buf, fuse_fill_dir_t callback, MemorySession scope) implements DirFiller {

	DirFillerImpl(MemoryAddress buf, MemoryAddress callback, MemorySession scope) {
		this(buf, fuse_fill_dir_t.ofAddress(callback, scope), scope);
	}

	@Override
	public int fill(String name, Consumer<Stat> statFiller, long offset, Set<FillDirFlags> flags) {
		MemoryAddress statAddr;
		if (statFiller != null) {
			var segment = stat.allocate(scope);
			statFiller.accept(new StatImpl(segment));
			statAddr = segment.address();
		} else {
			statAddr = MemoryAddress.NULL;
		}
		var encodedFlags = FillDirFlags.toMask(flags, fuse_h.FUSE_FILL_DIR_PLUS());
		return callback.apply(buf, scope.allocateUtf8String(name).address(), statAddr, offset, encodedFlags);
	}

}