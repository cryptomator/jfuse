package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.DirFiller;
import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.mac.extr.fuse_fill_dir_t;
import org.cryptomator.jfuse.mac.extr.stat;

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
		return callback.apply(buf, scope.allocateUtf8String(name).address(), statAddr, offset);
	}

}