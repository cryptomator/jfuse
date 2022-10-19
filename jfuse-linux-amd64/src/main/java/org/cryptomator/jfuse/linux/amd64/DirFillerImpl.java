package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.api.DirFiller;
import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.linux.amd64.extr.fuse_fill_dir_t;
import org.cryptomator.jfuse.linux.amd64.extr.stat;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySession;
import java.util.function.Consumer;

record DirFillerImpl(MemoryAddress buf, fuse_fill_dir_t callback, MemorySession scope) implements DirFiller {

	DirFillerImpl(MemoryAddress buf, MemoryAddress callback, MemorySession scope) {
		this(buf, fuse_fill_dir_t.ofAddress(callback, scope), scope);
	}

	@Override
	public int fill(String name, Consumer<Stat> statFiller, long offset, int flags) {
		var statSegment = stat.allocate(scope);
		statFiller.accept(new StatImpl(statSegment));
		return callback.apply(buf, scope.allocateUtf8String(name).address(), statSegment.address(), offset, flags);
	}

}