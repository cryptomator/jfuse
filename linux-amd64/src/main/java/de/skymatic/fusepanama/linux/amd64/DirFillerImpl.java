package de.skymatic.fusepanama.linux.amd64;

import de.skymatic.fusepanama.DirFiller;
import de.skymatic.fusepanama.Stat;
import de.skymatic.fusepanama.linux.amd64.lowlevel.fuse_fill_dir_t;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.ResourceScope;
import jdk.incubator.foreign.SegmentAllocator;

record DirFillerImpl(MemoryAddress buf, fuse_fill_dir_t callback, SegmentAllocator allocator) implements DirFiller {

	DirFillerImpl(MemoryAddress buf, MemoryAddress callback, ResourceScope scope) {
		this(buf, fuse_fill_dir_t.ofAddress(callback, scope), SegmentAllocator.nativeAllocator(scope));
	}

	@Override
	public int fill(String name, Stat stat, long offset) {
		var statAddr = stat instanceof StatImpl s ? s.segment().address() : MemoryAddress.NULL;
		return callback.apply(buf, allocator.allocateUtf8String(name).address(), statAddr, offset);
	}

}