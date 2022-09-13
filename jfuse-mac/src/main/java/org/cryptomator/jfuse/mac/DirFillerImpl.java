package org.cryptomator.jfuse.mac;

import org.cryptomator.jfuse.api.DirFiller;
import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.mac.extr.fuse_fill_dir_t;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySession;
import java.util.Set;

record DirFillerImpl(MemoryAddress buf, fuse_fill_dir_t callback, MemorySession scope) implements DirFiller {

	DirFillerImpl(MemoryAddress buf, MemoryAddress callback, MemorySession scope) {
		this(buf, fuse_fill_dir_t.ofAddress(callback, scope), scope);
	}

	@Override
	public int fill(String name, Stat stat, long offset, Set<FillDirFlags> flags) {
		var statAddr = stat instanceof StatImpl s ? s.segment().address() : MemoryAddress.NULL;
		return callback.apply(buf, scope.allocateUtf8String(name).address(), statAddr, offset);
	}

}