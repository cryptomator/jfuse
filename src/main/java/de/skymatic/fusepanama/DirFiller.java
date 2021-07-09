package de.skymatic.fusepanama;

import de.skymatic.fusepanama.lowlevel.fuse_fill_dir_t;
import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.ResourceScope;

public record DirFiller(MemoryAddress buf, fuse_fill_dir_t callback) {

	DirFiller(MemoryAddress buf, MemoryAddress callback) {
		this(buf, fuse_fill_dir_t.ofAddress(callback));
	}

	/**
	 * Inserts a single item into the directory entry buffer.
	 * @param name The file name
	 * @param stat Currently ignored, future use.
	 * @param offset The offset of the readdir-call plus the number of already filled entries
	 * @return 0 if readdir should continue to fill in further items, non-zero otherwise
	 */
	public int fill(String name, Stat stat, long offset) {
		try (var scope = ResourceScope.newConfinedScope()) {
			return callback.apply(buf, CLinker.toCString(name, scope).address(), MemoryAddress.NULL, offset);
		}
	}

}
