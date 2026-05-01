package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.DirFiller;
import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.mac.extr.fuse3.fuse_darwin_attr;
import org.cryptomator.jfuse.mac.extr.fuse3.fuse_fill_dir_t;
import org.cryptomator.jfuse.mac.extr.fuse3.stat;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.util.function.Consumer;

record DirFillerImpl(MemorySegment buf, MemorySegment callback, Arena arena, boolean darwinExtensions) implements DirFiller {

	@Override
	public int fill(String name, Consumer<Stat> statFiller, long offset, int flags) {
		MemorySegment segment;
		Stat statWrapper;
		if (darwinExtensions) {
			segment = fuse_darwin_attr.allocate(arena);
			statWrapper = new StatImpl(segment);
		} else {
			segment = stat.allocate(arena);
			statWrapper = new StatCompatImpl(segment);
		}
		statFiller.accept(statWrapper);
		return fuse_fill_dir_t.invoke(callback, buf, arena.allocateFrom(name), segment, offset, flags);
	}

}
