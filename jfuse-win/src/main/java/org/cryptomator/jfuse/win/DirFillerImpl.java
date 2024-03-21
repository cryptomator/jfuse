package org.cryptomator.jfuse.win;

import org.cryptomator.jfuse.api.DirFiller;
import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.win.extr.fuse3.fuse3_fill_dir_t;
import org.cryptomator.jfuse.win.extr.fuse3.fuse_stat;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.function.Consumer;

record DirFillerImpl(MemorySegment buf, MemorySegment callback, Arena arena) implements DirFiller {

	@Override
	public int fill(String name, Consumer<Stat> statFiller, long offset, int flags) {
		var statSegment = fuse_stat.allocate(arena);
		statFiller.accept(new StatImpl(statSegment));
		return fuse3_fill_dir_t.invoke(callback, buf, arena.allocateFrom(name), statSegment, offset, flags);
	}

}