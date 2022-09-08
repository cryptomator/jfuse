package org.cryptomator.jfuse.win.amd64;

import org.cryptomator.jfuse.api.DirFiller;
import org.cryptomator.jfuse.api.Stat;
import org.cryptomator.jfuse.win.amd64.extr.fuse_fill_dir_t;

import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySession;

record DirFillerImpl(MemoryAddress buf, fuse_fill_dir_t callback, MemorySession scope) implements DirFiller {

	DirFillerImpl(MemoryAddress buf, MemoryAddress callback, MemorySession scope) {
		this(buf, fuse_fill_dir_t.ofAddress(callback, scope), scope);
	}

	@Override
	public int fill(String name, Stat stat, long offset) {
		var statAddr = stat instanceof StatImpl s ? s.segment().address() : MemoryAddress.NULL;
		return callback.apply(buf, scope.allocateUtf8String(name).address(), statAddr, offset, 0); //TODO: readdir plus
	}

}