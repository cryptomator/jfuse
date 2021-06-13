package de.skymatic.fusepanama;

import de.skymatic.fusepanama.lowlevel.fuse_fill_dir_t;
import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.ResourceScope;

import java.nio.charset.StandardCharsets;

/**
 * {@link de.skymatic.fusepanama.lowlevel.fuse_fill_dir_t} is an upcall interface
 * and can't be used in order to invoke the "filler" callback that C expectes us to call.
 */
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
		System.out.println("fill");
		try (var scope = ResourceScope.newConfinedScope()) {
			// return (int) methodHandle.invokeExact(buf, CLinker.toCString(name, StandardCharsets.UTF_8, scope).address(), MemoryAddress.NULL, offset);
			return callback.apply(buf, CLinker.toCString(name, StandardCharsets.UTF_8, scope).address(), MemoryAddress.NULL, offset);
		}
	}

}
