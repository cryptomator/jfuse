package de.skymatic.fusepanama.mac;

import de.skymatic.fusepanama.DirFiller;
import de.skymatic.fusepanama.Stat;
import de.skymatic.fusepanama.mac.lowlevel.fuse_fill_dir_t;
import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.ResourceScope;

public record MacDirFiller(MemoryAddress buf, fuse_fill_dir_t callback) implements DirFiller {

	MacDirFiller(MemoryAddress buf, MemoryAddress callback) {
		this(buf, fuse_fill_dir_t.ofAddress(callback));
	}

	@Override
	public int fill(String name, Stat stat, long offset) {
		try (var scope = ResourceScope.newConfinedScope()) {
			var statAddr = stat instanceof MacStat s ? s.segment().address() : MemoryAddress.NULL;
			return callback.apply(buf, CLinker.toCString(name, scope).address(), statAddr, offset);
		}
	}

}