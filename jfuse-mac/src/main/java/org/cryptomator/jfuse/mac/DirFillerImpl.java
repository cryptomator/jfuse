package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.DirFiller;
import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.mac.extr.fuse.fuse_fill_dir_t;
import org.cryptomator.jfuse.mac.extr.fuse.stat;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.function.Consumer;

record DirFillerImpl(MemorySegment buf, MemorySegment callback, Arena arena) implements DirFiller {

	@Override
	public int fill(String name, Consumer<Stat> statFiller, long offset, int flags) {
		var statSegment = stat.allocate(arena);
		statFiller.accept(new StatImpl(statSegment));
		return fuse_fill_dir_t.invoke(callback, buf, arena.allocateFrom(name), statSegment, offset);
	}

}