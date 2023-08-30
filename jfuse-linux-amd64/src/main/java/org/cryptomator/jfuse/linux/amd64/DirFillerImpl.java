package org.cryptomator.jfuse.linux.amd64;

import org.cryptomator.jfuse.api.DirFiller;
import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.linux.amd64.extr.fuse3.fuse_fill_dir_t;
import org.cryptomator.jfuse.linux.amd64.extr.fuse3.stat;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.function.Consumer;

record DirFillerImpl(MemorySegment buf, fuse_fill_dir_t callback, Arena arena) implements DirFiller {

	DirFillerImpl(MemorySegment buf, MemorySegment callback, Arena arena) {
		this(buf, fuse_fill_dir_t.ofAddress(callback, arena), arena);
	}

	@Override
	public int fill(String name, Consumer<Stat> statFiller, long offset, int flags) {
		var statSegment = stat.allocate(arena);
		statFiller.accept(new StatImpl(statSegment));
		return callback.apply(buf, arena.allocateUtf8String(name), statSegment, offset, flags);
	}

}