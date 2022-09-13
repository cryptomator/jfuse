package org.cryptomator.jfuse.win.amd64;

import org.cryptomator.jfuse.api.DirFiller;
import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.win.amd64.extr.fuse3_fill_dir_t;
import org.cryptomator.jfuse.win.amd64.extr.fuse_h;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySession;
import java.util.Set;

record DirFillerImpl(MemoryAddress buf, fuse3_fill_dir_t callback, MemorySession scope) implements DirFiller {

	DirFillerImpl(MemoryAddress buf, MemoryAddress callback, MemorySession scope) {
		this(buf, fuse3_fill_dir_t.ofAddress(callback, scope), scope);
	}

	@Override
	public int fill(String name, Stat stat, long offset, Set<FillDirFlags> flags) {
		var statAddr = stat instanceof StatImpl s ? s.segment().address() : MemoryAddress.NULL;
		var encodedFlags = FillDirFlags.toMask(flags, fuse_h.FUSE_FILL_DIR_PLUS());
		return callback.apply(buf, scope.allocateUtf8String(name).address(), statAddr, offset, encodedFlags);
	}

}